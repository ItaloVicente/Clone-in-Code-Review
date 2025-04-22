			GitStore.ObjectInfo.Builder b = GitStore.ObjectInfo.newBuilder();
			b.setObjectType(GitStore.ObjectInfo.ObjectType.valueOf(getType()));
			b.setOffset(offsetOf(chunkPtr));
			b.setPackedSize(packedSize);
			b.setInflatedSize(inflatedSize);
			if (base != null) {
				byte[] t = new byte[Constants.OBJECT_ID_LENGTH];
				base.copyRawTo(t
				b.setDeltaBase(ByteString.copyFrom(t));
			}
			if (isFragmented())
				b.setIsFragmented(true);
			return new ObjectInfo(chunkKey
