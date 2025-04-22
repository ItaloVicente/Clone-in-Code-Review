
	private void writeReftable(DfsPackDescription pack
			throws IOException {
		ReftableWriter writer = new ReftableWriter();
		if (refBlockSize > 0) {
			writer.setRefBlockSize(refBlockSize);
		}
		if (refRestartInterval > 0) {
			writer.setRestartInterval(refRestartInterval);
		}
		try (OutputStream out = objdb.writeFile(pack
			writer.begin(out).sortAndWriteRefs(refs).finish();
			pack.addFileExt(REFTABLE);
			pack.setFileSize(REFTABLE
		}
	}
