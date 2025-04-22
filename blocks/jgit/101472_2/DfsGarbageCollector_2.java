
	private void writeReftable(DfsPackDescription pack
			throws IOException {
		ReftableWriter writer = new ReftableWriter();
		if (refBlockSize > 0) {
			writer.setRefBlockSize(refBlockSize);
		}
		if (refRestartInterval > 0) {
			writer.setRestartInterval(refRestartInterval);
		}
		try (CountingOutputStream cnt = new CountingOutputStream(
				objdb.writeFile(pack
			writer.begin(cnt).sortAndWriteRefs(refs).finish();
			pack.addFileExt(REFTABLE);
			pack.setFileSize(REFTABLE
		}
	}
