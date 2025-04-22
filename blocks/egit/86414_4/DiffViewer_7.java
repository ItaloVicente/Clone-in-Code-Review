	public static class Configuration
			extends HyperlinkSourceViewer.Configuration {

		public Configuration(IPreferenceStore preferenceStore) {
			super(preferenceStore);
		}

		@Override
		public int getHyperlinkStateMask(ISourceViewer sourceViewer) {
			return SWT.NONE;
		}

		@Override
		protected IHyperlinkDetector[] internalGetHyperlinkDetectors(
				ISourceViewer sourceViewer) {
			Assert.isTrue(sourceViewer instanceof DiffViewer);
			IHyperlinkDetector[] result = { new HyperlinkDetector() };
			return result;
		}

		@Override
		public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
			Assert.isTrue(sourceViewer instanceof DiffViewer);
			DiffViewer viewer = (DiffViewer) sourceViewer;
			return viewer.tokens.keySet()
					.toArray(new String[viewer.tokens.size()]);
		}

		@Override
		public IPresentationReconciler getPresentationReconciler(
				ISourceViewer sourceViewer) {
			Assert.isTrue(sourceViewer instanceof DiffViewer);
			DiffViewer viewer = (DiffViewer) sourceViewer;
			PresentationReconciler reconciler = new PresentationReconciler();
			reconciler.setDocumentPartitioning(
					getConfiguredDocumentPartitioning(viewer));
			for (String contentType : viewer.tokens.keySet()) {
				DefaultDamagerRepairer damagerRepairer = new DefaultDamagerRepairer(
						new SingleTokenScanner(
								() -> viewer.tokens.get(contentType)));
				reconciler.setDamager(damagerRepairer, contentType);
				reconciler.setRepairer(damagerRepairer, contentType);
			}
			return reconciler;
		}

	}

