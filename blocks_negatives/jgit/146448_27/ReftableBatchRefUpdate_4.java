
	private static List<Ref> toNewRefs(RevWalk rw, List<ReceiveCommand> pending)
			throws IOException {
		List<Ref> refs = new ArrayList<>(pending.size());
		for (ReceiveCommand cmd : pending) {
			String name = cmd.getRefName();
			ObjectId newId = cmd.getNewId();
			String newSymref = cmd.getNewSymref();
			if (AnyObjectId.isEqual(ObjectId.zeroId(), newId)
					&& newSymref == null) {
				refs.add(new ObjectIdRef.Unpeeled(NEW, name, null));
				continue;
			} else if (newSymref != null) {
				refs.add(new SymbolicRef(name,
						new ObjectIdRef.Unpeeled(NEW, newSymref, null)));
				continue;
			}

			RevObject obj = rw.parseAny(newId);
			RevObject peel = null;
			if (obj instanceof RevTag) {
				peel = rw.peel(obj);
			}
			if (peel != null) {
				refs.add(new ObjectIdRef.PeeledTag(PACKED, name, newId,
						peel.copy()));
			} else {
				refs.add(new ObjectIdRef.PeeledNonTag(PACKED, name, newId));
			}
		}
		return refs;
	}

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
		List<Reftable> readers = stack.readers();
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
		List<Reftable> stack = refdb.stack().readers();
		Reftable last = stack.get(stack.size() - 1);

		List<Reftable> tables = new ArrayList<>(2);
		tables.add(last);
		tables.add(new ReftableReader(BlockSource.from(newTable)));

		ReftableCompactor compactor = new ReftableCompactor();
		compactor.setConfig(cfg);
		compactor.setIncludeDeletes(true);
		compactor.addAll(tables);
		compactor.compact(out);
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
		}
		return true;
	}
