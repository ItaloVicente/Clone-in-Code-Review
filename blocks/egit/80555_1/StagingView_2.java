		IBaseLabelProvider labelProvider = createLabelProvider(stagedViewer);
		labelProvider.addListener(new ILabelProviderListener() {

			@Override
			public void labelProviderChanged(LabelProviderChangedEvent event) {
				updateCommitButtons();
			}
		});
		stagedViewer.setLabelProvider(labelProvider);

