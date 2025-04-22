			if (cell != null) {
				try {
					m.setMeta(ChunkMeta.parseFrom(cell.getValue()));
				} catch (InvalidProtocolBufferException err) {
					callback.onFailure(new DhtException(MessageFormat.format(
							DhtText.get().invalidChunkMeta
					return;
				}
			}
