		super.removeListener(listener);
		provider.removeListener(listener);
		if (decorator != null) {
			decorator.removeListener(listener);
		}
		listeners.remove(listener);
	}

	public void setLabelDecorator(ILabelDecorator decorator) {
		ILabelDecorator oldDecorator = this.decorator;
		if (oldDecorator != decorator) {
			if (oldDecorator != null) {
