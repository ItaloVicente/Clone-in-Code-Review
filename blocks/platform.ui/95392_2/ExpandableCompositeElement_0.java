	@Override
	public void reset() {
		super.reset();
		getExpandableComposite().setTitleBarForeground(null);
	}

	private ExpandableComposite getExpandableComposite() {
		return (ExpandableComposite) getNativeWidget();
	}
