
		TextSourceViewerConfiguration configuration = new TextSourceViewerConfiguration(
				EditorsUI.getPreferenceStore()) {

			public int getHyperlinkStateMask(ISourceViewer sourceViewer) {
				return SWT.NONE;
			}

			@Override
			public IHyperlinkPresenter getHyperlinkPresenter(
					ISourceViewer sourceViewer) {
				return new MultipleHyperlinkPresenter(PlatformUI.getWorkbench()
						.getDisplay().getSystemColor(SWT.COLOR_BLUE).getRGB()) {

					@Override
					public void hideHyperlinks() {
					}

				};
			}

			public IHyperlinkDetector[] getHyperlinkDetectors(ISourceViewer sourceViewer) {
				return getRegisteredHyperlinkDetectors(sourceViewer);
			}
		};

		commentViewer.configure(configuration);

