	private void writeReftable(DfsObjDatabase objdb
			ReftableCompactor compact) throws IOException {
		try (DfsOutputStream out = objdb.writeFile(pack
			compact.setConfig(configureReftable(reftableConfig
			compact.compact(out);
			pack.addFileExt(REFTABLE);
			pack.setReftableStats(compact.getStats());
		}
	}

