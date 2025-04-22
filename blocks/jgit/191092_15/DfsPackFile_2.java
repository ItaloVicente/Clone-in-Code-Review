	private DfsBlockCache.Ref<PackObjectSizeIndex> loadObjectSizeIdx(
			DfsReader ctx
		ctx.stats.readObjectSizeIndex++;
		long start = System.nanoTime();
		try (ReadableChannel rc = ctx.db.openFile(desc
			objectSizeIndex = PackObjectSizeIndexLoader
					.load(Channels.newInputStream(rc));
			long size = rc.position();
			ctx.stats.readObjectSizeIndexBytes += size;
			ctx.stats.readObjectSizeIndexMicros += elapsedMicros(start);
			return new DfsBlockCache.Ref<>(objectSizeIndexKey
					size
		} catch (EOFException e) {
			throw new IOException(
					MessageFormat.format(DfsText.get().shortReadOfIndex
							desc.getFileName(OBJECT_SIZE_INDEX))
					e);
		}
	}

