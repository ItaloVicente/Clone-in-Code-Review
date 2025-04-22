	private String getNameRev(RevCommit commit) throws MissingObjectException,
			IOException {
		if (repo == null) {
			return ""; //$NON-NLS-1$
		}

		Map<String, Ref> tagsMap = repo.getTags();
		UtilCommit current = (UtilCommit) revWalk.parseCommit(commit);

		for (Map.Entry<String, Ref> entry : tagsMap.entrySet()) {
			RevObject any = revWalk.peel(revWalk.parseAny(entry.getValue()
					.getObjectId()));

			if (!(any instanceof UtilCommit)) {
				continue;
			}

			UtilCommit newTag = (UtilCommit) any;
			fillNames(newTag, entry.getKey(), 0, 0, current.getCommitTime()
					- CUTOFF_DATE_SLOP, revWalk);
		}

		return current.getUtil() == null ? "" : String.valueOf(current.getUtil()); //$NON-NLS-1$
	}

	private void fillNames(UtilCommit commit, String name, int generation,
			int distance, int cutoff, UtilWalk revWalk)
			throws MissingObjectException, IncorrectObjectTypeException,
			IOException {
		if (commit.getCommitTime() < cutoff) {
			return;
		}

		NameInfo nameInfo = (NameInfo) commit.getUtil();

		if (nameInfo == null) {
			nameInfo = new NameInfo();
			commit.setUtil(nameInfo);
		} else if (nameInfo.distance <= distance) {
			return;
		}

		nameInfo.name = name;
		nameInfo.distance = distance;
		nameInfo.generation = generation;

		for (int parent_number = 0; parent_number < commit.getParentCount(); parent_number++) {
			UtilCommit parent = (UtilCommit) revWalk.parseCommit(commit
					.getParent(parent_number).getId());

			if (parent_number > 0) {
				String new_name;

				if (generation > 0) {
					new_name = String.format(
							"%s~%d^%d", name, generation, parent_number + 1); //$NON-NLS-1$
				} else {
					new_name = String.format("%s^%d", name, parent_number + 1); //$NON-NLS-1$
				}

				fillNames(parent, new_name, 0, distance
						+ MERGE_TRAVERSAL_WEIGHT, cutoff, revWalk);
			} else {
				fillNames(parent, name, generation + 1, distance + 1, cutoff,
						revWalk);
			}
		}
	}

	private String tagOf(final RevCommit c) {
		try {
			return getNameRev(c);
		} catch (Exception e) {
			return ""; //$NON-NLS-1$
		}
	}

