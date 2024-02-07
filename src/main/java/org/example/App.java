package org.example;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) throws Exception {
        ZKClient zkClient = new ZKClient();
        zkClient.getChildren("/");
        Thread.sleep(Long.MAX_VALUE);
    }
}
