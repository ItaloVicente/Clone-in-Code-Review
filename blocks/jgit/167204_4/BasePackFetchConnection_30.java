					if (TransferConfig.ProtocolVersion.V0
							.equals(getProtocolVersion())) {
						multiAck = MultiAck.OFF;
						resultsPending = 0;
						receivedAck = true;
						if (statelessRPC) {
							state.writeTo(out
						}
						break SEND_HAVES;
