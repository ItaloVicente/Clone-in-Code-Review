				SWT.NONE) {

			protected IContentAssistant createContentAssistant(
					ISourceViewer viewer) {
				ContentAssistant assistant = new ContentAssistant();
				assistant.enableAutoInsert(true);
				Collection<String> paths = getFileList();
				final CommitProposalProcessor processor = new CommitProposalProcessor(
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
			}

		};
