		this.labelProviderListener = new ILabelProviderListener() {
			@Override
			public void labelProviderChanged(LabelProviderChangedEvent event) {
				fireLabelProviderChanged(event);
			}
		};
