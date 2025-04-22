			@Override
			public IPresentationReconciler getPresentationReconciler(
					ISourceViewer viewer) {
				PresentationReconciler reconciler = new PresentationReconciler();
				reconciler.setDocumentPartitioning(
						getConfiguredDocumentPartitioning(sourceViewer));
				HyperlinkDamagerRepairer hyperlinkDamagerRepairer = new HyperlinkDamagerRepairer(
						new HyperlinkTokenScanner(
								getHyperlinkDetectors(sourceViewer),
								sourceViewer));
				reconciler.setDamager(hyperlinkDamagerRepairer,
						IDocument.DEFAULT_CONTENT_TYPE);
				reconciler.setRepairer(hyperlinkDamagerRepairer,
						IDocument.DEFAULT_CONTENT_TYPE);
				return reconciler;
			}

