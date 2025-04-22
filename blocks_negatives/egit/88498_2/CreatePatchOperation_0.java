		diffFmt.setProgressMonitor(gitMonitor);
		diffFmt.setContext(contextLines);

		if (headerFormat != null && headerFormat != DiffHeaderFormat.NONE)
			writeGitPatchHeader(sb);

		diffFmt.setRepository(repository);
		diffFmt.setPathFilter(pathFilter);
