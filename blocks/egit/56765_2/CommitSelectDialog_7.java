		CommitLabelProvider labelProvider = new CommitLabelProvider();
		labelProvider.addListener(new ILabelProviderListener() {
			@Override
			public void labelProviderChanged(LabelProviderChangedEvent event) {
				tv.refresh();
			}
		});
		tv.setLabelProvider(labelProvider);
