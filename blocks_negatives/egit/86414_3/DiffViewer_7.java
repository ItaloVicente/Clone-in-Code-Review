		configure(new HyperlinkSourceViewer.Configuration(
				EditorsUI.getPreferenceStore()) {

			@Override
			public int getHyperlinkStateMask(ISourceViewer sourceViewer) {
				return SWT.NONE;
			}

			@Override
			protected IHyperlinkDetector[] internalGetHyperlinkDetectors(
					ISourceViewer sourceViewer) {
				IHyperlinkDetector[] result = { new HyperlinkDetector() };
				return result;
			}

			@Override
			public String[] getConfiguredContentTypes(
					ISourceViewer sourceViewer) {
				return tokens.keySet().toArray(new String[tokens.size()]);
			}
