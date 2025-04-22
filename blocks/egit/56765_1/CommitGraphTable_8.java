		GraphLabelProvider graphLabelProvider = new GraphLabelProvider(
				canShowEmailAddresses);
		graphLabelProvider.addListener(new ILabelProviderListener() {
			@Override
			public void labelProviderChanged(LabelProviderChangedEvent event) {
				table.refresh();
			}
		});
