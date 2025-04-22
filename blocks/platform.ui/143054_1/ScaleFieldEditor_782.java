	private int incrementValue;

	private int maxValue;

	private int minValue;

	private int oldValue;

	private int pageIncrementValue;

	protected Scale scale;

	public ScaleFieldEditor(String name, String labelText, Composite parent) {
		super(name, labelText, parent);
		setDefaultValues();
	}

	public ScaleFieldEditor(String name, String labelText, Composite parent,
			int min, int max, int increment, int pageIncrement) {
		super(name, labelText, parent);
		setValues(min, max, increment, pageIncrement);
	}

	@Override
