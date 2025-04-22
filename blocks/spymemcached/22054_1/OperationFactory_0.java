package net.spy.memcached;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.Map;

public class Mike {
  public static void main(String args[]) throws Exception {
    MemcachedClient client = new MemcachedClient(new BinaryConnectionFactory(),
        Arrays.asList(new InetSocketAddress("localhost", 11211)));
    Map<SocketAddress, Map<String, String>> stats = client.getStats("vkey k 0");
    client.shutdown();
  }
}
