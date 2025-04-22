		DiffRegionFormatter diffFormatter = new DiffRegionFormatter(
				document);
		diffFormatter.setContext(1);
		diffFormatter.setRepository(revision.getRepository());
		diffFormatter.format(interestingDiff, diff.getOldText(),
				diff.getNewText());

		try (ObjectReader reader = revision.getRepository().newObjectReader()) {
			DiffEntry diffEntry = CompareCoreUtils.getChangeDiffEntry(
					revision.getRepository(), revision.getSourcePath(),
					revision.getCommit(), parent, reader);
			if (diffEntry != null) {
				FileDiff fileDiff = new FileDiff(revision.getCommit(),
						diffEntry);
				document.setDefault(revision.getRepository(), fileDiff);
