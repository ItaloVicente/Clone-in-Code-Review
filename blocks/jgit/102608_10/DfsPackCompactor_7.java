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
		int bs = out.blockSize();
		if (bs > 0) {
			cfg = new ReftableConfig(cfg);
			cfg.setRefBlockSize(bs);
			cfg.setAlignBlocks(true);
		}
		return cfg;
	}

