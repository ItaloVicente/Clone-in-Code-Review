			@Override
			public int getHyperlinkStateMask(ISourceViewer viewer) {
				return SWT.NONE;
			}

			@Override
			public IReconciler getReconciler(ISourceViewer viewer) {
				if (!isEditable(viewer)) {
					return null;
				}
				return super.getReconciler(sourceViewer);
			}

