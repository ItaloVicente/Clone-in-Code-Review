
	private void writeReftable() throws IOException {
		if (reftableConfig != null) {
			DfsPackDescription pack = objdb.newPack(GC);
			newPackDesc.add(pack);
			writeReftable(pack);
		}
	}

	private void writeReftable(DfsPackDescription pack) throws IOException {
		if (!hasGcReftable()) {
			writeReftable(pack
			return;
		}

		try (ReftableStack stack = ReftableStack.open(ctx
			ReftableCompactor compact = new ReftableCompactor();
			compact.addAll(stack.readers());
			compact.setIncludeDeletes(true);
			compactReftable(pack
		}
	}

	private boolean hasGcReftable() {
		for (DfsReftable table : reftablesBefore) {
			if (table.getPackDescription().getPackSource() == GC) {
				return true;
			}
		}
		return false;
	}

	private void writeReftable(DfsPackDescription pack
			throws IOException {
		try (DfsOutputStream out = objdb.writeFile(pack
			ReftableConfig cfg = configureReftable(reftableConfig
			ReftableWriter writer = new ReftableWriter(cfg);
			writer.setMinUpdateIndex(1);
			writer.setMaxUpdateIndex(1);
			writer.begin(out).sortAndWriteRefs(refs).finish();
			pack.addFileExt(REFTABLE);
			pack.setReftableStats(writer.getStats());
		}
	}

	private void compactReftable(DfsPackDescription pack
			ReftableCompactor compact) throws IOException {
		try (DfsOutputStream out = objdb.writeFile(pack
			compact.setConfig(configureReftable(reftableConfig
			compact.compact(out);
			pack.addFileExt(REFTABLE);
			pack.setReftableStats(compact.getStats());
		}
	}
