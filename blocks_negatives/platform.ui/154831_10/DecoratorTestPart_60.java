		listener = new ILabelProviderListener() {

			@Override
			public void labelProviderChanged(LabelProviderChangedEvent event) {
				endTime = System.currentTimeMillis() + DELAY_TIME;

			}
		};
