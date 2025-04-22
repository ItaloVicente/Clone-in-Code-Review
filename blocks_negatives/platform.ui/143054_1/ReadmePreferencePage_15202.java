public class ReadmePreferencePage extends PreferencePage implements
        IWorkbenchPreferencePage, SelectionListener, ModifyListener {
    private Button radioButton1;

    private Button radioButton2;

    private Button radioButton3;

    private Button checkBox1;

    private Button checkBox2;

    private Button checkBox3;

    private Text textField;

    /**
     * Creates an new checkbox instance and sets the default
     * layout data.
     *
     * @param group  the composite in which to create the checkbox
     * @param label  the string to set into the checkbox
     * @return the new checkbox
     */
    private Button createCheckBox(Composite group, String label) {
        Button button = new Button(group, SWT.CHECK | SWT.LEFT);
        button.setText(label);
        button.addSelectionListener(this);
        GridData data = new GridData();
        button.setLayoutData(data);
        return button;
    }

    /**
     * Creates composite control and sets the default layout data.
     *
     * @param parent  the parent of the new composite
     * @param numColumns  the number of columns for the new composite
     * @return the newly-created coposite
     */
    private Composite createComposite(Composite parent, int numColumns) {
        Composite composite = new Composite(parent, SWT.NULL);

        GridLayout layout = new GridLayout();
        layout.numColumns = numColumns;
        composite.setLayout(layout);

        GridData data = new GridData();
        data.verticalAlignment = GridData.FILL;
        data.horizontalAlignment = GridData.FILL;
        composite.setLayoutData(data);
        return composite;
    }

    /** (non-Javadoc)
     * Method declared on PreferencePage
     */
    @Override
