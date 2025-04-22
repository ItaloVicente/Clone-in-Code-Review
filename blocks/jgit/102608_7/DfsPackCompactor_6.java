	private void writeReftable(DfsObjDatabase objdb
			ReftableCompactor compact) throws IOException {
		try (DfsOutputStream out = objdb.writeFile(pack
			compact.setConfig(configureReftable(reftableConfig
			compact.compact(out);
			pack.addFileExt(REFTABLE);
			pack.setReftableStats(compact.getStats());
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

