			@Override
			public IHyperlinkPresenter getHyperlinkPresenter(
					ISourceViewer targetViewer) {
				return new MultipleHyperlinkPresenter(PlatformUI.getWorkbench()
						.getDisplay().getSystemColor(SWT.COLOR_BLUE).getRGB()) {

					@Override
					public void hideHyperlinks() {
					}

				};
			}

			public IHyperlinkDetector[] getHyperlinkDetectors(
					ISourceViewer targetViewer) {
				return getRegisteredHyperlinkDetectors(sourceViewer);
			}

