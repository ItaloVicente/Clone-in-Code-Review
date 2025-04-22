package net.spy.memcached.ops;


public interface GetAndTouchOperation extends KeyedOperation {

	interface Callback extends OperationCallback {
		void gotData(String key, int flags, byte[] data);
	}

}
