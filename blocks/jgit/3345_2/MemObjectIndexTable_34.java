				chunkKey = ChunkKey.fromBytes(colInfo.suffix(cell.getName()));
				try {
					chunks.add(new ObjectInfo(
							chunkKey
							cell.getTimestamp()
							GitStore.ObjectInfo.parseFrom(cell.getValue())));
				} catch (InvalidProtocolBufferException badCell) {
					callback.onFailure(new DhtException(MessageFormat.format(
							DhtText.get().invalidObjectInfo
							badCell));
					return;
				}
