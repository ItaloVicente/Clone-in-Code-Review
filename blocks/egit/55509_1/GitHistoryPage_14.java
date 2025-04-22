			@Override
			public String[] getConfiguredContentTypes(
					ISourceViewer sourceViewer) {
				return new String[] { IDocument.DEFAULT_CONTENT_TYPE,
						CommitMessageViewer.HEADER_CONTENT_TYPE,
						CommitMessageViewer.FOOTER_CONTENT_TYPE };
