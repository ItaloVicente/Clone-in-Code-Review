
	public void fireNodeLabelChanged(Object object) {
		LabelProviderChangedEvent event = new LabelProviderChangedEvent(this,
				object);
		fireLabelProviderChanged(event);
	}
