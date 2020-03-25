package com.test.sdk;

import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.api.RpcClient;
import org.apache.flume.api.RpcClientFactory;
import org.apache.flume.event.EventBuilder;

import java.nio.charset.Charset;
import java.util.HashMap;

public class MyRpcClient {
    private RpcClient client;
    private String hostname;
    private int port;

    public void init(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
        this.client = RpcClientFactory.getDefaultInstance(hostname, port);
    }

    public void sendDataToFlume(String data) {
        HashMap<String, String> map = new HashMap<String, String>(16) {
            {
                put("topic", "test1");
            }
        };
        Event event = EventBuilder.withBody(data, Charset.forName("UTF-8"), map);
        try {
            client.append(event);
        } catch (EventDeliveryException e) {
            client.close();
            client = null;
            client = RpcClientFactory.getDefaultInstance(hostname, port);
        }
    }

    public void cleanUp() {
        client.close();
    }
}
