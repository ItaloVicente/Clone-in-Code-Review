package net.spy.memcached.ops;

import net.spy.memcached.tapmessage.Opcode;
import net.spy.memcached.tapmessage.ResponseMessage;


public interface TapOperation extends KeyedOperation {

	interface Callback extends OperationCallback {
		public void gotData(ResponseMessage message);

		public void gotAck(Opcode opcode, int opaque);
	}

	public void streamClosed(OperationState state);
}
