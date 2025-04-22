
	private void writeReftable(DfsPackDescription pack
			throws IOException {
		try (DfsOutputStream out = objdb.writeFile(pack
			ReftableConfig cfg = configureReftable(reftableConfig
			ReftableWriter writer = new ReftableWriter(cfg);
			writer.begin(out).sortAndWriteRefs(refs).finish();
			pack.addFileExt(REFTABLE);
			pack.setReftableStats(writer.getStats());
		}
	}

	static ReftableConfig configureReftable(ReftableConfig cfg
			DfsOutputStream out) {
		cfg = new ReftableConfig(cfg);
		if (out.blockSize() >= 256) {
			cfg.setRefBlockSize(out.blockSize());
		} else {
			int sz = DfsBlockCache.getInstance().getBlockSize();
			if (cfg.getRefBlockSize() < sz) {
				cfg.setRefBlockSize(sz);
			}
		}
		return cfg;
	}
