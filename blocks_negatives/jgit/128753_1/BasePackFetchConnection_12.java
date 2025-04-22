					if (minimalNegotiationSet != null
							&& minimalNegotiationSet.isEmpty()) {
						if (statelessRPC) {
							state.writeTo(out, null);
						}
						break SEND_HAVES;
					}
