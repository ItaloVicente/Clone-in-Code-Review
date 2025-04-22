	private static boolean matchOld(ReceiveCommand cmd, @Nullable Ref ref) {
		if (ref == null) {
			return AnyObjectId.isEqual(ObjectId.zeroId(), cmd.getOldId())
					&& cmd.getOldSymref() == null;
		} else if (ref.isSymbolic()) {
			return ref.getTarget().getName().equals(cmd.getOldSymref());
		}
		ObjectId id = ref.getObjectId();
		if (id == null) {
			id = ObjectId.zeroId();
		}
		return cmd.getOldId().equals(id);
	}

	private void applyUpdates(RevWalk rw, List<ReceiveCommand> pending)
			throws IOException {
		List<Ref> newRefs = toNewRefs(rw, pending);
		long updateIndex = nextUpdateIndex();
		Set<DfsPackDescription> prune = Collections.emptySet();
		DfsPackDescription pack = odb.newPack(PackSource.INSERT);
		try (DfsOutputStream out = odb.writeFile(pack, REFTABLE)) {
			ReftableConfig cfg = DfsPackCompactor
					.configureReftable(reftableConfig, out);

			ReftableWriter.Stats stats;
			if (refdb.compactDuringCommit()
					&& newRefs.size() * AVG_BYTES <= cfg.getRefBlockSize()
					&& canCompactTopOfStack(cfg)) {
				ByteArrayOutputStream tmp = new ByteArrayOutputStream();
				write(tmp, cfg, updateIndex, newRefs, pending);
				stats = compactTopOfStack(out, cfg, tmp.toByteArray());
				prune = toPruneTopOfStack();
			} else {
				stats = write(out, cfg, updateIndex, newRefs, pending);
			}
			pack.addFileExt(REFTABLE);
			pack.setReftableStats(stats);
		}

		odb.commitPack(Collections.singleton(pack), prune);
		odb.addReftable(pack, prune);
		refdb.clearCache();
	}

	private ReftableWriter.Stats write(OutputStream os, ReftableConfig cfg,
			long updateIndex, List<Ref> newRefs, List<ReceiveCommand> pending)
			throws IOException {
		ReftableWriter writer = new ReftableWriter(cfg)
				.setMinUpdateIndex(updateIndex).setMaxUpdateIndex(updateIndex)
				.begin(os).sortAndWriteRefs(newRefs);
