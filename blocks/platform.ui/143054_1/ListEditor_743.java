	private List list;

	private Composite buttonBox;

	private Button addButton;

	private Button removeButton;

	private Button upButton;

	private Button downButton;

	private SelectionListener selectionListener;

	protected ListEditor() {
	}

	protected ListEditor(String name, String labelText, Composite parent) {
		init(name, labelText);
		createControl(parent);
	}

	private void addPressed() {
		setPresentsDefaultValue(false);
		String input = getNewInputObject();

		if (input != null) {
			int index = list.getSelectionIndex();
			if (index >= 0) {
