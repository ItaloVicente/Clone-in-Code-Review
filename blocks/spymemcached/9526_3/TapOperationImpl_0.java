				if (response.getFlags() == TAP_FLAG_ACK) {
					((Callback)getCallback()).gotAck(response.getOpcode(), response.getOpaque());
				}
				if (response.getOpcode() != TapOpcode.OPAQUE && response.getOpcode() != TapOpcode.NOOP) {
					((Callback)getCallback()).gotData(response);
				}
				message = null;
				bytesProcessed = 0;
