
package net.spy.memcached.ops;

public class MultiReplicaGetOperationCallback extends MultiOperationCallback implements
    ReplicaGetOperation.Callback {

  public MultiReplicaGetOperationCallback(OperationCallback original, int todo) {
    super(original, todo);
  }

  public void gotData(String key, int flags, byte[] data) {
    ((ReplicaGetOperation.Callback) originalCallback).gotData(key, flags, data);
  }
}
