	private String dialogMessage;

	FilterListEditor(String name, String label, String addButtonLabel, String removeButtonLabel,
			String dialogMessage, Composite parent) {
		super(name, label, parent);
		this.dialogMessage = dialogMessage;
		getAddButton().setText(addButtonLabel);
		getRemoveButton().setText(removeButtonLabel);
