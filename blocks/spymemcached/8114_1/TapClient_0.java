			Object m = rqueue.poll(time, timeunit);
			if (m == null) {
				return null;
			} else if (m instanceof ResponseMessage) {
				return (ResponseMessage)m;
			} else if (m instanceof TapAck) {
				TapAck ack = (TapAck)m;
				tapAck(ack.getConn(), ack.getOpcode(), ack.getOpaque(), ack.getCallback());
				return null;
			} else {
				throw new RuntimeException("Unexpected tap message type");
			}
