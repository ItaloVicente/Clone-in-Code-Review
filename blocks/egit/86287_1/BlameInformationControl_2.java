		try (ObjectReader reader = revision.getRepository().newObjectReader()) {
			DiffEntry diffEntry = CompareCoreUtils.getChangeDiffEntry(
					revision.getRepository(), revision.getSourcePath(),
					revision.getCommit(), parent, reader);
			if (diffEntry != null) {
				FileDiff fileDiff = new FileDiff(revision.getCommit(),
						diffEntry);
				document.setDefault(revision.getRepository(), fileDiff);
			}
		}
		document.connect(diffFormatter);
