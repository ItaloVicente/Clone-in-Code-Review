			public IPresentationReconciler getPresentationReconciler(
					ISourceViewer viewer) {
				PresentationReconciler reconciler = new PresentationReconciler();
				reconciler.setDocumentPartitioning(
						getConfiguredDocumentPartitioning(commentViewer));
				DefaultDamagerRepairer hyperlinkDamagerRepairer = new DefaultDamagerRepairer(
						new HyperlinkTokenScanner(
								getHyperlinkDetectors(commentViewer),
								commentViewer));
				reconciler.setDamager(hyperlinkDamagerRepairer,
						IDocument.DEFAULT_CONTENT_TYPE);
				reconciler.setRepairer(hyperlinkDamagerRepairer,
						IDocument.DEFAULT_CONTENT_TYPE);
				TextAttribute headerDefault = new TextAttribute(
						PlatformUI.getWorkbench().getDisplay()
								.getSystemColor(SWT.COLOR_DARK_GRAY));
				DefaultDamagerRepairer headerDamagerRepairer = new DefaultDamagerRepairer(
						new HyperlinkTokenScanner(
								getHyperlinkDetectors(commentViewer),
								commentViewer, headerDefault));
				reconciler.setDamager(headerDamagerRepairer,
						CommitMessageViewer.HEADER_CONTENT_TYPE);
				reconciler.setRepairer(headerDamagerRepairer,
						CommitMessageViewer.HEADER_CONTENT_TYPE);
				DefaultDamagerRepairer footerDamagerRepairer = new DefaultDamagerRepairer(
						new FooterTokenScanner(
								getHyperlinkDetectors(commentViewer),
								commentViewer));
				reconciler.setDamager(footerDamagerRepairer,
						CommitMessageViewer.FOOTER_CONTENT_TYPE);
				reconciler.setRepairer(footerDamagerRepairer,
						CommitMessageViewer.FOOTER_CONTENT_TYPE);
				return reconciler;
