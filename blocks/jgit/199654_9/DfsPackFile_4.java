
	private DfsBlockCache.Ref<CommitGraph> loadCommitGraph(DfsReader ctx
			DfsStreamKey cgkey) throws IOException {
		ctx.stats.readCommitGraph++;
		long start = System.nanoTime();
		try (ReadableChannel rc = ctx.db.openFile(desc
			long size;
			CommitGraph cg;
			try {
				InputStream in = Channels.newInputStream(rc);
				int wantSize = 8192;
				int bs = rc.blockSize();
				if (0 < bs && bs < wantSize) {
					bs = (wantSize / bs) * bs;
				} else if (bs <= 0) {
					bs = wantSize;
				}
				in = new BufferedInputStream(in
				cg = CommitGraphLoader.read(in);
			} finally {
				size = rc.position();
				ctx.stats.readCommitGraphBytes += size;
				ctx.stats.readCommitGraphMicros += elapsedMicros(start);
			}
			commitGraph = cg;
			return new DfsBlockCache.Ref<>(cgkey
		} catch (IOException e) {
			throw new IOException(
					MessageFormat.format(DfsText.get().cannotReadCommitGraph
							desc.getFileName(COMMIT_GRAPH))
					e);
		}
	}
