			if (TransferConfig.ProtocolVersion.V2
					.equals(getProtocolVersion())) {
				state = new TemporaryBuffer.Heap(Integer.MAX_VALUE);
				pckState = new PacketLineOut(state);
				try {
					doFetchV2(monitor
				} finally {
					clearState();
				}
				return;
			}
