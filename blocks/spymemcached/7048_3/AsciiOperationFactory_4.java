package net.spy.memcached.ops;

import net.spy.memcached.internal.SyncResponse;


public interface SyncOperation extends KeyedOperation {

	interface Callback extends OperationCallback {
		void gotData(SyncResponse s);
	}

}
