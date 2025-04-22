    /**
     * Value that will feed Scale.setIncrement(int).
     */
    private int incrementValue;

    /**
     * Value that will feed Scale.setMaximum(int).
     */
    private int maxValue;

    /**
     * Value that will feed Scale.setMinimum(int).
     */
    private int minValue;

    /**
     * Old integer value.
     */
    private int oldValue;

    /**
     * Value that will feed Scale.setPageIncrement(int).
     */
    private int pageIncrementValue;

    /**
     * The scale, or <code>null</code> if none.
     */
    protected Scale scale;

    /**
     * Creates a scale field editor.
     *
     * @param name
     *            the name of the preference this field editor works on
     * @param labelText
     *            the label text of the field editor
     * @param parent
     *            the parent of the field editor's control
     */
    public ScaleFieldEditor(String name, String labelText, Composite parent) {
        super(name, labelText, parent);
        setDefaultValues();
    }

    /**
     * Creates a scale field editor with particular scale values.
     *
     * @param name
     *            the name of the preference this field editor works on
     * @param labelText
     *            the label text of the field editor
     * @param parent
     *            the parent of the field editor's control
     * @param min
     *            the value used for Scale.setMinimum(int).
     * @param max
     *            the value used for Scale.setMaximum(int).
     * @param increment
     *            the value used for Scale.setIncrement(int).
     * @param pageIncrement
     *            the value used for Scale.setPageIncrement(int).
     */
    public ScaleFieldEditor(String name, String labelText, Composite parent,
            int min, int max, int increment, int pageIncrement) {
        super(name, labelText, parent);
        setValues(min, max, increment, pageIncrement);
    }

    @Override
