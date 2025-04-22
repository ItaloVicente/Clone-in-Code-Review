	private class CreatePatchAction extends Action {

		private TreeWalk walker;

		public CreatePatchAction() {
			super(UIText.GitHistoryPage_CreatePatch);
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
				String path = saveFileDialog.open();
				if (path == null)
					return;
				File file = new File(new Path(path).toOSString());

				StringBuilder sb = new StringBuilder();
				DiffFormatter diffFmt = new DiffFormatter();
				try {
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

			try {
				name = URLEncoder.encode(name, "UTF-8"); //$NON-NLS-1$
			} catch (UnsupportedEncodingException e) {
			}
			name = name.concat(".patch"); //$NON-NLS-1$
			return name;
		}

		private void writePatch(SWTCommit commit, StringBuilder sb,
				DiffFormatter diffFmt) throws IOException {

			final SimpleDateFormat dtfmt;
			dtfmt = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z", Locale.US); //$NON-NLS-1$
			dtfmt.setTimeZone(commit.getAuthorIdent().getTimeZone());
			sb.append(UIText.GitHistoryPage_From).append(" "). //$NON-NLS-1$
				append(commit.getId().getName()).append(" "). //$NON-NLS-1$
				append(dtfmt.format(Long.valueOf(commit.getCommitTime()))).append("\n"); //$NON-NLS-1$
			sb.append(UIText.GitHistoryPage_From).append(": "). //$NON-NLS-1$
				append(commit.getAuthorIdent().getName()).
				append(" <").append(commit.getAuthorIdent().getEmailAddress()).//$NON-NLS-1$
				append(">\n"); //$NON-NLS-1$
			sb.append(UIText.GitHistoryPage_Date).append(": "). //$NON-NLS-1$
				append(dtfmt.format(System.currentTimeMillis())).
				append("\n"); //$NON-NLS-1$
			sb.append(UIText.GitHistoryPage_Subject).append(": [PATCH] "). //$NON-NLS-1$
				append(commit.getShortMessage()).
				append("\n\n"); //$NON-NLS-1$

			sb.append(commit.getFullMessage()).append("\n\n"); //$NON-NLS-1$

			FileDiff[] diffs = FileDiff.compute(walker, commit);
			for (FileDiff diff : diffs) {
				sb.append("diff --git a").append(IPath.SEPARATOR). //$NON-NLS-1$
						append(diff.path).append(" b").append(IPath.SEPARATOR). //$NON-NLS-1$
						append(diff.path).append("\n"); //$NON-NLS-1$
				diff.outputDiff(sb, db, diffFmt);
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
