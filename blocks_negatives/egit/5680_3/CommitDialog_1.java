		commitText = new SpellcheckableMessageArea(messageArea, commitMessage,
				SWT.NONE) {

			protected IContentAssistant createContentAssistant(
					ISourceViewer viewer) {
				ContentAssistant assistant = new ContentAssistant();
				assistant.enableAutoInsert(true);
				Collection<String> paths = getFileList();
				Collection<String> messages = CommitMessageHistory.getCommitHistory();
				final CommitProposalProcessor processor = new CommitProposalProcessor(
						messages.toArray(new String[messages.size()]),
						paths.toArray(new String[paths.size()]));
				viewer.getTextWidget().addDisposeListener(
						new DisposeListener() {

							public void widgetDisposed(DisposeEvent e) {
								processor.dispose();
							}
						});
				assistant.setContentAssistProcessor(processor,
						IDocument.DEFAULT_CONTENT_TYPE);
				return assistant;
