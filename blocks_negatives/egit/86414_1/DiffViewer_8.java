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
