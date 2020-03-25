package com.test.sdk;

public class App {
    public static void main(String[] args) {
        MyRpcClient client = new MyRpcClient();
        client.init("k8s-master01", 32131);

        String sampleData = "Hello Kafka ~~~";
        for (int i = 0; i < 10; i++) {
            client.sendDataToFlume(sampleData);
        }

        client.cleanUp();
    }
}
