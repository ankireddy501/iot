package com.parking.management;

import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;

import static java.lang.System.out;

/**
 * Created by anki on 18-06-2016.
 */
public class MessageSubscriber {

    private Pubnub pubnub;
    private String parking_channel = "parking_channel";
    private String publish_key = "pub-c-b11b8c65-22c5-4bc7-84d2-dd41949dbc58";
    private String subscribe_key = "sub-c-313d4a34-2ef7-11e6-b700-0619f8945a4f";

    public MessageSubscriber(String publish_key, String subscribe_key) {
        System.out.println("intialized");
        this.publish_key = publish_key;
        this.subscribe_key = subscribe_key;
    }

    public void init(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                    System.out.println("---->"+Thread.currentThread().getName());
                    pubnub = new Pubnub(publish_key, subscribe_key);
                    pubnub.setCacheBusting(false);
                    subscribe(parking_channel);
                    System.out.println("<----"+Thread.currentThread().getName());
                while(true){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        },"subscriber thread").start();
    }

    private void notifyUser(Object message) {
        out.println(message.toString());
    }

    private void subscribe(final String channel) {

        try {
            System.out.println("setting the subscribe channel");
            pubnub.subscribe(channel, new Callback() {

                @Override
                public void connectCallback(String channel, Object message) {
                    notifyUser("SUBSCRIBE : CONNECT on channel:" + channel + " : " + message.getClass() + " : "
                            + message.toString());
                }

                @Override
                public void disconnectCallback(String channel, Object message) {
                    notifyUser("SUBSCRIBE : DISCONNECT on channel:" + channel + " : " + message.getClass() + " : "
                            + message.toString());
                }

                public void reconnectCallback(String channel, Object message) {
                    notifyUser("SUBSCRIBE : RECONNECT on channel:" + channel + " : " + message.getClass() + " : "
                            + message.toString());
                }

                @Override
                public void successCallback(String channel, Object message) {
                    notifyUser("SUBSCRIBE : " + channel + " : " + message.getClass() + " : " + message.toString());

                }

                @Override
                public void successCallback(String channel, Object message, String timetoken) {
                    notifyUser("SUBSCRIBE : [TT - " + timetoken + "] " + channel + " : " + message.getClass() + " : "
                            + message.toString());

                }

                @Override
                public void errorCallback(String channel, PubnubError error) {

                    /*
                     *
                     * # Switch on error code, see PubnubError.java
                     *
                     * if (error.errorCode == 112) { # Bad Auth Key!
                     * unsubscribe, get a new auth key, subscribe, etc... } else
                     * if (error.errorCode == 113) { # Need to set Auth Key !
                     * unsubscribe, set auth, resubscribe }
                     */

                    notifyUser("SUBSCRIBE : ERROR on channel " + channel + " : " + error.toString());
                    if (error.errorCode == PubnubError.PNERR_TIMEOUT)
                        pubnub.disconnectAndResubscribe();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
