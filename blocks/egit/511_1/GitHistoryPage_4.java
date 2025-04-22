	private class CreatePatchAction extends Action {

		private TreeWalk walker;
		private boolean isGit;

		public CreatePatchAction(boolean isGit) {
			super(isGit ? UIText.GitHistoryPage_CreateGitPatch : UIText.GitHistoryPage_CreatePatch);
			this.isGit = isGit;
		}

		@Override
		public void run() {
			IStructuredSelection selection = ((IStructuredSelection) revObjectSelectionProvider
					.getSelection());
			if (selection.size() == 1) {

				Iterator<?> it = selection.iterator();
				SWTCommit commit = (SWTCommit) it.next();

				FileDialog saveFileDialog = new FileDialog(ourControl
						.getShell(), SWT.SAVE);
				saveFileDialog.setText(UIText.GitHistoryPage_SelectPatchFile);
				saveFileDialog.setFileName(createFileName(commit));
				saveFileDialog.setOverwrite(true);
				String path = saveFileDialog.open();
				if (path == null)
					return;
				File file = new File(new Path(path).toOSString());

				StringBuilder sb = new StringBuilder();
				DiffFormatter diffFmt = new DiffFormatter();
				try {
					if (isGit)
						writeGitPatch(commit, sb, diffFmt);
					else
						writePatch(commit, sb, diffFmt);

					Writer output = new BufferedWriter(new FileWriter(file));
					try {
						output.write(sb.toString());
					} finally {
						output.close();
					}
				} catch (IOException e) {
					Activator.logError(UIText.GitHistoryPage_ErrorNotWritten, e);
				}

			}

		}

		private String createFileName(SWTCommit commit) {
			String name = commit.getShortMessage();

			name = name.trim();
			try {
				name = URLEncoder.encode(name, "UTF-8"); //$NON-NLS-1$
			} catch (UnsupportedEncodingException e) {
			}
			if (name.length() > 80)
				name = name.substring(0, 80);
			while (name.endsWith(".")) //$NON-NLS-1$
				name = name.substring(0, name.length() - 1);
			name = name.concat(".patch"); //$NON-NLS-1$
			return name;
		}

		private void writeGitPatch(SWTCommit commit, StringBuilder sb,
				DiffFormatter diffFmt) throws IOException {

			final SimpleDateFormat dtfmt;
			dtfmt = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z", Locale.US); //$NON-NLS-1$
			dtfmt.setTimeZone(commit.getAuthorIdent().getTimeZone());
			sb.append(UIText.GitHistoryPage_From).append(" "). //$NON-NLS-1$
					append(commit.getId().getName()).append(" "). //$NON-NLS-1$
					append(dtfmt.format(Long.valueOf(System.currentTimeMillis())))
					.append("\n"); //$NON-NLS-1$
			sb
					.append(UIText.GitHistoryPage_From)
					.append(": "). //$NON-NLS-1$
					append(commit.getAuthorIdent().getName())
					.append(" <").append(commit.getAuthorIdent().getEmailAddress()).//$NON-NLS-1$
					append(">\n"); //$NON-NLS-1$
			sb.append(UIText.GitHistoryPage_Date).append(": "). //$NON-NLS-1$
					append(dtfmt.format(Long.valueOf(commit.getCommitTime())))
					.append("\n"); //$NON-NLS-1$
			sb.append(UIText.GitHistoryPage_Subject).append(": [PATCH] "). //$NON-NLS-1$
					append(commit.getShortMessage());

			String message = commit.getFullMessage().substring(
					commit.getShortMessage().length());
			sb.append(message).append("\n---\n"); //$NON-NLS-1$

			FileDiff[] diffs = FileDiff.compute(walker, commit);
			for (FileDiff diff : diffs) {
				sb.append("diff --git a").append(IPath.SEPARATOR). //$NON-NLS-1$
				append(diff.path).append(" b").append(IPath.SEPARATOR). //$NON-NLS-1$
				append(diff.path).append("\n"); //$NON-NLS-1$
				diff.outputDiff(sb, db, diffFmt, false);
			}
			sb.append("\n--\n"); //$NON-NLS-1$
			Bundle bundle = Activator.getDefault().getBundle();
			String name = (String) bundle.getHeaders().get(org.osgi.framework.Constants.BUNDLE_NAME);
			String version = (String) bundle.getHeaders().get(org.osgi.framework.Constants.BUNDLE_VERSION);
			sb.append(name). append(" ").append(version); //$NON-NLS-1$
		}

		private void writePatch(SWTCommit commit, StringBuilder sb,
				DiffFormatter diffFmt) throws IOException {
			FileDiff[] diffs = FileDiff.compute(walker, commit);
			for (FileDiff diff : diffs) {
				sb.append("diff --git ").append(diff.path).append(" ").//$NON-NLS-1$ //$NON-NLS-2$
					append(diff.path)
						.append("\n"); //$NON-NLS-1$
				diff.outputDiff(sb, db, diffFmt, true);
			}
		}
		public void setTreeWalk(TreeWalk walker) {
			this.walker = walker;
		}

		@Override
		public boolean isEnabled() {
			IStructuredSelection selection = ((IStructuredSelection) revObjectSelectionProvider
					.getSelection());
			Iterator<?> it = selection.iterator();
			SWTCommit commit = (SWTCommit) it.next();
			return (commit.getParentCount() == 1);

		}

	}
