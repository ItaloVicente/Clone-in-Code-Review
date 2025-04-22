					if (TransferConfig.ProtocolVersion.V0
							.equals(getProtocolVersion())) {
						multiAck = MultiAck.OFF;
						resultsPending = 0;
						receivedAck = true;
						if (statelessRPC) {
							state.writeTo(out, null);
						}
						break SEND_HAVES;
					}
					markCommon(walk.parseAny(ackId), AckNackResult.ACK_COMMON);
