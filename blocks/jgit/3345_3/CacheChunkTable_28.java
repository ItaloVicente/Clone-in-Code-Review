				ChunkMeta meta;
				try {
					meta = ChunkMeta.parseFrom(ent.getValue());
				} catch (InvalidProtocolBufferException e) {
					client.modify(singleton(Change.remove(ent.getKey()))
							Sync.<Void> none());
					continue;
				}
