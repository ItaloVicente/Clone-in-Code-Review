			@Override
			public IHyperlinkPresenter getHyperlinkPresenter(
					ISourceViewer targetViewer) {
				return new MultipleHyperlinkPresenter(PlatformUI.getWorkbench()
						.getDisplay().getSystemColor(SWT.COLOR_BLUE).getRGB());
			}

