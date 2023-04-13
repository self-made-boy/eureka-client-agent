package org.example;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;


import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;


public class Agent {

    public static void premain(String agentArgs, Instrumentation instrumentation) {

        System.out.println("agent start...");
        instrumentation.addTransformer(new CustomClassFileTransformer());

    }

    public static class CustomClassFileTransformer implements ClassFileTransformer {


        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

            if (!"com/netflix/discovery/shared/transport/jersey/JerseyApplicationClient".equals(className)) {
                return classfileBuffer;
            }
            System.out.println("transform ...");
            ClassPool pool = ClassPool.getDefault();
            try {
                CtClass jerseyApplicationClientClazz = pool.get("com.netflix.discovery.shared.transport.jersey.JerseyApplicationClient");
                CtClass stringType = pool.get("java.lang.String");
                CtClass clientType = pool.get("com.sun.jersey.api.client.Client");
                CtClass mapType = pool.get("java.util.Map");

                CtConstructor ctConstructor = jerseyApplicationClientClazz.getDeclaredConstructor(new CtClass[]{clientType, stringType, mapType});
                ctConstructor.insertBefore("org.example.Assistant.addHeaders(additionalHeaders);");
                return jerseyApplicationClientClazz.toBytecode();
            } catch(Exception e){
                e.printStackTrace();
            }
            return classfileBuffer;
        }
    }

}



