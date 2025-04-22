			GitStore.ObjectInfo.Builder b = GitStore.ObjectInfo.newBuilder();
			b.setObjectType(ObjectType.valueOf(type));
			b.setOffset((int) getOffset());
			b.setPackedSize(packed);
			b.setInflatedSize(inflated);
			return new ObjectInfo(key
