
		final LabelProviderChangedEvent event = new LabelProviderChangedEvent(
				this, elementList.toArray(new Object[elementList.size()]));
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				fireLabelProviderChanged(event);
			}
		});
