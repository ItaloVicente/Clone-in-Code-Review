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

			@Override
			public IPresentationReconciler getPresentationReconciler(
					ISourceViewer viewer) {
				PresentationReconciler reconciler = new PresentationReconciler();
				reconciler.setDocumentPartitioning(
						getConfiguredDocumentPartitioning(viewer));
				for (String contentType : tokens.keySet()) {
					DefaultDamagerRepairer damagerRepairer = new DefaultDamagerRepairer(
							new SingleTokenScanner(contentType));
					reconciler.setDamager(damagerRepairer, contentType);
					reconciler.setRepairer(damagerRepairer, contentType);
				}
				return reconciler;
			}
		});
