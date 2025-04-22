	private static class CrashingHyperlinkDetector
			extends AbstractHyperlinkDetector {

		@Override
		public IHyperlink[] detectHyperlinks(ITextViewer textViewer,
				IRegion region, boolean canShowMultipleHyperlinks) {
			throw new IllegalStateException(
					"CrashingHyperlinkDetector fails on purpose");
		}

	}
