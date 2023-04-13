package org.example;

import java.util.Map;

public class Assistant {
    public static void addHeaders(Map<String, String> additionalHeaders) {
        System.out.println("com.netflix.discovery.shared.transport.jersey.JerseyApplicationClient enhance header");
        if (additionalHeaders == null) {
            System.out.println("additionalHeaders is null");
           return;
        }

        for (Map.Entry<Object, Object> entry : System.getProperties().entrySet()) {
            String key = (String) entry.getKey();
            if (key.startsWith("eureka.header.")) {
                String headerKey = key.substring(14);
                String headerValue = (String) entry.getValue();
                additionalHeaders.put(headerKey, headerValue);
                System.out.println("eureka add header: "+headerKey+":"+headerValue);
            }
        }
    }
}
