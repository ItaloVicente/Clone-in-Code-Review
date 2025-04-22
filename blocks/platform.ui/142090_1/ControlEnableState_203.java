	protected ControlEnableState(Control w, List<Control> exceptions) {
		super();
		states = new ArrayList<>();
		this.exceptions = exceptions;
		readStateForAndDisable(w);
	}
