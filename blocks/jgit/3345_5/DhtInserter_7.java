
				GitStore.ObjectInfo.Builder b = GitStore.ObjectInfo.newBuilder();
				b.setObjectType(ObjectType.valueOf(type));
				b.setOffset(position);
				b.setPackedSize(packedSize);
				b.setInflatedSize(inflatedSize);
				ObjectInfo info = new ObjectInfo(key
