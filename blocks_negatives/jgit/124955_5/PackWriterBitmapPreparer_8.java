		List<BitmapBuilderEntry> orderedTipCommitBitmaps = new ArrayList<>(
				tipCommitBitmaps.size());
		while (!tipCommitBitmaps.isEmpty()) {
			BitmapBuilderEntry largest =
					Collections.max(tipCommitBitmaps, ORDER_BY_CARDINALITY);
			tipCommitBitmaps.remove(largest);
			orderedTipCommitBitmaps.add(largest);

			for (int i = tipCommitBitmaps.size() - 1; i >= 0; i--) {
				tipCommitBitmaps.get(i).getBuilder()
						.andNot(largest.getBuilder());
			}
		}

		return new CommitSelectionHelper(peeledWant, commits, pos,
				orderedTipCommitBitmaps, reuse, reuseCommits);
