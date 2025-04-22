						if (TransferConfig.ProtocolVersion.V2
								.equals(getProtocolVersion())) {
							multiAck = MultiAck.OFF;
							resultsPending = 0;
							break SEND_HAVES;
						}
