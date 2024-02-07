package org.example;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;

import java.io.IOException;
import java.util.List;

public class ZKClient {

    private static String connectInfo = "192.168.30.128:2181,192.168.30.129:2181,192.168.30.130:2181";

    private static int sessionTimeout = 2000;

    private ZooKeeper zkClient;

    public ZooKeeper getZkClient() {
        return zkClient;
    }

    public void setZkClient(ZooKeeper zkClient) {
        this.zkClient = zkClient;
    }

    /**
     * 创建zk客户端
     * @throws Exception
     */
    public ZKClient() throws Exception {
        zkClient = new ZooKeeper(connectInfo, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println(watchedEvent.getType() + "--" +watchedEvent.getPath());

                /**
                 * 监听默认只能使用一次，再次启动监听。
                 * true：监听path的子znode。监听节点的增减（数据的修改set监听不到）
                 */
                List<String> children;
                try {
                    children = zkClient.getChildren("/", true);
                    for (String child : children) {
                        System.out.println("---"+child);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    /**
     * 创建子节点
     * @param path
     * @param data
     * @param acl 权限
     * @param mode 节点类型（持久，临时）
     * @throws Exception
     */
    public void createNode(String path, String data, List<ACL> acl, CreateMode mode) throws Exception {
        zkClient.create(path, data.getBytes(), acl, mode);
    }

    /**
     * 获取子节点
     * @param path
     * @throws Exception
     */
    public void getChildren(String path) throws Exception {
        List<String> children = zkClient.getChildren(path, true);
        for (String child : children) {
            System.out.println(child);
        }
    }
}
