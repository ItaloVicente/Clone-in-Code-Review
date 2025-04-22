	private DfsBlockCache.Ref<PackObjectSizeIndex> loadObjectSizeIdx(
			DfsReader ctx
			DfsStreamKey objIdxKey) throws IOException {
		ctx.stats.readObjSizeIdx++;
		long start = System.nanoTime();
		try (ReadableChannel rc = ctx.db.openFile(desc
			objSizeIdx = PackObjectSizeIndexLoader
					.load(Channels.newInputStream(rc));
			long size = rc.position();
			ctx.stats.readObjSizeBytes += size;
			ctx.stats.readObjSizeMicros += elapsedMicros(start);
			return new DfsBlockCache.Ref<>(objIdxKey
					objSizeIdx);
		} catch (EOFException e) {
			throw new IOException(
					MessageFormat.format(DfsText.get().shortReadOfIndex
							desc.getFileName(OBJECT_SIZE_INDEX))
					e);
		}
	}

