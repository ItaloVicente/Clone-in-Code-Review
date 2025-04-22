
package com.couchbase.client.vbucket;

import com.couchbase.client.vbucket.config.Config;
import java.util.ArrayList;
import java.util.List;
import net.spy.memcached.MemcachedNode;

public class NMVInjectingVBucketNodeLocator extends VBucketNodeLocator {

  ArrayList<String> bogused; // chosen for size over speed, only 20 ops needed

  public NMVInjectingVBucketNodeLocator(List<MemcachedNode> nodes,
    Config jsonConfig) {
    super(nodes, jsonConfig);
    bogused = new ArrayList<String>();
  }

  @Override
  public int getVBucketIndex(String key) {

    int vBucketIndex = super.getVBucketIndex(key);

    if (bogused.contains(key)) {
      System.err.println("Already bogused once: " + key);
      return vBucketIndex;
    }

    if (key.startsWith("bogus")) {
      vBucketIndex = 1025; // make the vbucket index bogus first time through
      System.err.println("BOGUS!!!!! " + key );
      bogused.add(key);
    }

    return vBucketIndex;
  }

}
