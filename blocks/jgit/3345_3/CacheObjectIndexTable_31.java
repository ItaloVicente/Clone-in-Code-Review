
				private byte[] encode(List<ObjectInfo> items) {
					CachedObjectIndex.Builder b;
					b = CachedObjectIndex.newBuilder();
					for (ObjectInfo info : items) {
						CachedObjectIndex.Item.Builder i = b.addItemBuilder();
						i.setChunkKey(info.getChunkKey().asString());
						i.setObjectInfo(info.getData());
						if (0 < info.getTime())
							i.setTime(info.getTime());
					}
					return b.build().toByteArray();
				}
