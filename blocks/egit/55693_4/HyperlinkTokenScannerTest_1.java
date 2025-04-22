		when(configuration.getHyperlinkDetectors(viewer)).thenReturn(detectors);
		when(preferenceStore
				.getBoolean(AbstractTextEditor.PREFERENCE_HYPERLINKS_ENABLED))
						.thenReturn(true);
		when(preferenceStore.getBoolean(
				"org.eclipse.ui.internal.editors.text.URLHyperlinkDetector"))
						.thenReturn(false);
		HyperlinkTokenScanner scanner = new HyperlinkTokenScanner(configuration,
				viewer, preferenceStore, null);
