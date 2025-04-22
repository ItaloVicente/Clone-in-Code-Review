package net.spy.memcached.ops;

import net.spy.memcached.tapmessage.TapOpcode;
import net.spy.memcached.tapmessage.ResponseMessage;


public interface TapOperation extends KeyedOperation {

	interface Callback extends OperationCallback {
		public void gotData(ResponseMessage message);

		public void gotAck(TapOpcode opcode, int opaque);
	}

	public void streamClosed(OperationState state);
}
