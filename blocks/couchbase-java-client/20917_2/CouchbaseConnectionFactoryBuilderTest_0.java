package com.couchbase.client;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import net.spy.memcached.PersistTo;
import net.spy.memcached.ReplicateTo;

public class ObserveTests {
  public static void main(String[] args) throws Exception{
    CouchbaseConnectionFactoryBuilder cfb = new CouchbaseConnectionFactoryBuilder();
    cfb.setObsPollInterval(50);
    cfb.setObsPollMax(2000);
    List<URI> uris = new LinkedList<URI>();
    uris.add(URI.create("http://localhost:9000/pools"));
    CouchbaseClient client = new CouchbaseClient(cfb.buildCouchbaseConnection(uris, "default", ""));

    String key = "key" + Math.random() * 1000;
    System.out.println(client.set(key, 0, "value", PersistTo.TWO, ReplicateTo.ZERO)
        .getStatus().getMessage());
    System.out.println(client.delete(key, PersistTo.TWO, ReplicateTo.ZERO)
        .getStatus().getMessage());

    client.shutdown();
  }
}
