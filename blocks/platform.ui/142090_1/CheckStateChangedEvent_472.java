	public CheckStateChangedEvent(ICheckable source, Object element,
			boolean state) {
		super(source);
		this.element = element;
		this.state = state;
	}
