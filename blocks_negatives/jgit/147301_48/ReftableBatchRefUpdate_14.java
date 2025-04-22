	private long nextUpdateIndex() throws IOException {
		long updateIndex = 0;
		for (Reftable r : refdb.stack().readers()) {
			if (r instanceof ReftableReader) {
				updateIndex = Math.max(updateIndex,
						((ReftableReader) r).maxUpdateIndex());
			}
		}
		return updateIndex + 1;
	}

	private boolean canCompactTopOfStack(ReftableConfig cfg)
			throws IOException {
		DfsReftableStack stack = refdb.stack();
		List<ReftableReader> readers = stack.readers();
		if (readers.isEmpty()) {
			return false;
		}

		int lastIdx = readers.size() - 1;
		DfsReftable last = stack.files().get(lastIdx);
		DfsPackDescription desc = last.getPackDescription();
		if (desc.getPackSource() != PackSource.INSERT
				|| !packOnlyContainsReftable(desc)) {
			return false;
		}

		Reftable table = readers.get(lastIdx);
		int bs = cfg.getRefBlockSize();
		return table instanceof ReftableReader
				&& ((ReftableReader) table).size() <= 3 * bs;
	}

	private ReftableWriter.Stats compactTopOfStack(OutputStream out,
			ReftableConfig cfg, byte[] newTable) throws IOException {
		List<ReftableReader> stack = refdb.stack().readers();
		ReftableReader last = stack.get(stack.size() - 1);

		List<ReftableReader> tables = new ArrayList<>(2);
		tables.add(last);
		tables.add(new ReftableReader(BlockSource.from(newTable)));

		ReftableCompactor compactor = new ReftableCompactor(out);
		compactor.setConfig(cfg);
		compactor.setIncludeDeletes(true);
		compactor.addAll(tables);
		compactor.compact();
		return compactor.getStats();
	}

	private Set<DfsPackDescription> toPruneTopOfStack() throws IOException {
		List<DfsReftable> stack = refdb.stack().files();
		DfsReftable last = stack.get(stack.size() - 1);
		return Collections.singleton(last.getPackDescription());
	}

	private boolean packOnlyContainsReftable(DfsPackDescription desc) {
		for (PackExt ext : PackExt.values()) {
			if (ext != REFTABLE && desc.hasFileExt(ext)) {
				return false;
			}
