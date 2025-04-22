package net.spy.memcached.ops;


public interface GetlOperation extends KeyedOperation {

	interface Callback extends OperationCallback {
		void gotData(String key, int flags, long cas, byte[] data);
	}

}
