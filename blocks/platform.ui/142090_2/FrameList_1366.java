	public static final String P_CURRENT_FRAME = "currentFrame"; //$NON-NLS-1$

	public static final String P_RESET = "reset"; //$NON-NLS-1$

	private IFrameSource source;

	private List frames;

	private int current;

	public FrameList(IFrameSource source) {
		this.source = source;
		init();
	}

	public void addPropertyChangeListener(IPropertyChangeListener listener) {
		addListenerObject(listener);
	}

	public void back() {
		if (current > 0) {
			setCurrent(current - 1);
		}
	}

	protected void firePropertyChange(PropertyChangeEvent event) {
		Object[] listeners = getListeners();
		for (Object listener : listeners) {
			((IPropertyChangeListener) listener).propertyChange(event);
		}
	}

	public void forward() {
		if (current < frames.size() - 1) {
			setCurrent(current + 1);
		}
	}

	public Frame getCurrentFrame() {
		return getFrame(current);
	}

	public int getCurrentIndex() {
		return current;
	}

	public Frame getFrame(int index) {
		if (index < 0 || index >= frames.size()) {
