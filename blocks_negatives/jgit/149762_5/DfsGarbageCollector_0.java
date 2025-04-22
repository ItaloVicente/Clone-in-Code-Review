
	private void compactReftable(DfsPackDescription pack,
			ReftableCompactor compact) throws IOException {
		try (DfsOutputStream out = objdb.writeFile(pack, REFTABLE)) {
			compact.setConfig(configureReftable(reftableConfig, out));
			compact.compact(out);
			pack.addFileExt(REFTABLE);
			pack.setReftableStats(compact.getStats());
		}
	}
