		if (!CommitErrorWarningsUtil.canCommitWithCurrentErrors(getSite().getShell(),
				currentRepository, getStagedFileNames())
				|| !CommitErrorWarningsUtil.canCommitWithCurrentWarnings(getSite()
						.getShell(), currentRepository, getStagedFileNames()))
			return;

