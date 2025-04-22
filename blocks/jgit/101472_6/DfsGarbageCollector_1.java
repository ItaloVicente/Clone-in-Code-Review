
	private void writeReftable(DfsPackDescription pack
			throws IOException {
		try (DfsOutputStream out = objdb.writeFile(pack
			if (out.blockSize() >= 256) {
				reftableConfig = new ReftableConfig(reftableConfig);
				reftableConfig.setRefBlockSize(out.blockSize());
			}

			ReftableWriter writer = new ReftableWriter();
			writer.setConfig(reftableConfig);
			writer.begin(out).sortAndWriteRefs(refs).finish();
			pack.addFileExt(REFTABLE);
			pack.setReftableStats(writer.getStats());
		}
	}
