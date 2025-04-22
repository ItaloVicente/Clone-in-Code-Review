			@Override
			public String[] getConfiguredContentTypes(ISourceViewer viewer) {
				if (!useCommentHighlight) {
					return super.getConfiguredContentTypes(viewer);
				}
				return new String[] { IDocument.DEFAULT_CONTENT_TYPE,
						COMMENT_CONTENT_TYPE };
			}

