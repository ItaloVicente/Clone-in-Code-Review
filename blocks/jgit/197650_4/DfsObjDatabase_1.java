			n = new PackListImpl(o.packs
					o.commitGraphs);
		} while (!packList.compareAndSet(o
	}

	void addCommitGraph(DfsPackDescription add
			throws IOException {
		PackList o
		do {
			o = packList.get();
			if (o == NO_PACKS) {
				o = scanPacks(o);
				for (DfsCommitGraph cg : o.commitGraphs) {
					if (cg.getPackDescription().equals(add)) {
						return;
					}
				}
			}

			List<DfsCommitGraph> commitGraphs = new ArrayList<>(
					1 + o.commitGraphs.length);
			for (DfsCommitGraph cg : o.commitGraphs) {
				if (!remove.contains(cg.getPackDescription())) {
					commitGraphs.add(cg);
				}
			}
			commitGraphs.add(new DfsCommitGraph(add));
			n = new PackListImpl(o.packs
					commitGraphs.toArray(new DfsCommitGraph[0]));
