	private void writeGitPatch(StringBuilder sb, DiffFormatter diffFmt)
			throws IOException {

		final SimpleDateFormat dtfmt;
		dtfmt = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.US); //$NON-NLS-1$
		dtfmt.setTimeZone(commit.getAuthorIdent().getTimeZone());
				.append(dtfmt.format(Long.valueOf(System.currentTimeMillis())))
		sb.append(UIText.GitHistoryPage_From)
				.append(commit.getAuthorIdent().getName())
				.append(dtfmt.format(commit.getAuthorIdent().getWhen()))
				.append(commit.getShortMessage());

		String message = commit.getFullMessage().substring(
				commit.getShortMessage().length());

		FileDiff[] diffs = FileDiff.compute(walker, commit);
		for (FileDiff diff : diffs) {
			diff.outputDiff(sb, db, diffFmt, false, false);
		}
		Bundle bundle = Activator.getDefault().getBundle();
		String name = (String) bundle.getHeaders().get(
				org.osgi.framework.Constants.BUNDLE_NAME);
		String version = (String) bundle.getHeaders().get(
				org.osgi.framework.Constants.BUNDLE_VERSION);
	}

	private void writePatch(StringBuilder sb, DiffFormatter diffFmt)
			throws IOException {
		FileDiff[] diffs = FileDiff.compute(walker, commit);
		for (FileDiff diff : diffs) {
			String projectRelativePath = getProjectRelaticePath(diff);
			diff.outputDiff(sb, db, diffFmt, true, true);
		}
	}

	private String getProjectRelaticePath(FileDiff diff) {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		IPath absolutePath = new Path(db.getWorkTree().getAbsolutePath())
				.append(diff.getPath());
		IResource resource = root.getFileForLocation(absolutePath);
		return resource.getProjectRelativePath().toString();
	}

