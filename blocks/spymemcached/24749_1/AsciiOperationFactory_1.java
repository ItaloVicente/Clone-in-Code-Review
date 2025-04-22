
package net.spy.memcached.ops;

import net.spy.memcached.ReplicaIndex;

public interface ReplicaGetOperation extends KeyedOperation {

  interface Callback extends OperationCallback {
    void gotData(String key, int flags, byte[] data);
  }

  ReplicaIndex getReplicaIndex();
}
