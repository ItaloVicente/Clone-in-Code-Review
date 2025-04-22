	private final class CommitDragSourceListener extends DragSourceAdapter {
		@Override
		public void dragStart(DragSourceEvent event) {
			RevCommit commit = getSelectedCommit();
			event.doit = commit.getParentCount() == 1;
		}

		public void dragSetData(DragSourceEvent event) {
			boolean isFileTransfer = FileTransfer.getInstance()
					.isSupportedType(event.dataType);
			boolean isTextTransfer = TextTransfer.getInstance()
					.isSupportedType(event.dataType);
			if (isFileTransfer || isTextTransfer) {
				RevCommit commit = getSelectedCommit();
				String patchContent = createPatch(commit);
				if (isTextTransfer) {
					event.data = patchContent;
					return;
				} else {
					File patchFile = null;
					try {
						patchFile = createTempFile(commit);
						writeToFile(patchFile.getAbsolutePath(), patchContent);
						event.data = new String[] { patchFile.getAbsolutePath() };
					} catch (IOException e) {
						Activator.logError(NLS.bind(
								UIText.CommitGraphTable_UnableToWritePatch,
								commit.getId().name()), e);
					} finally {
						if (patchFile != null) {
							patchFile.deleteOnExit();
						}
					}
				}
			}
		}

		private File createTempFile(RevCommit commit) {
			String tmpDir = System.getProperty("java.io.tmpdir"); //$NON-NLS-1$
			File patchDir = new File(tmpDir, "egit-patch" + commit.getId().name()); //$NON-NLS-1$
			int counter = 1;
			while(patchDir.exists()) {
				patchDir = new File(tmpDir, commit.getId().name() + "_" + counter); //$NON-NLS-1$
			}
			patchDir.mkdir();
			patchDir.deleteOnExit();
			File patchFile;
			String suggestedFileName = CreatePatchOperation
					.suggestFileName(commit);
			patchFile = new File(patchDir, suggestedFileName);
			return patchFile;
		}

		private String createPatch(RevCommit commit) {
			Repository repository = input.getRepository();
			CreatePatchOperation operation = new CreatePatchOperation(
					repository, commit);
			operation.useGitFormat(true);
			operation.setContextLines(CreatePatchOperation.DEFAULT_CONTEXT_LINES);
			try {
				operation.execute(null);
			} catch (CoreException e) {
				Activator.logError(NLS.bind(
						UIText.CommitGraphTable_UnableToCreatePatch, commit
								.getId().name()), e);
			}
			String patchContent = operation.getPatchContent();
			return patchContent;
		}

		private RevCommit getSelectedCommit() {
			IStructuredSelection selection = (IStructuredSelection) table
					.getSelection();
			RevCommit commit = (RevCommit) selection.getFirstElement();
			return commit;
		}

		private void writeToFile(final String fileName, String content)
				throws IOException {
			Writer output = new BufferedWriter(new FileWriter(fileName));
			try {
				output.write(content);
			} finally {
				output.close();
			}
		}
	}

