    public InputDialog(Shell parentShell, String dialogTitle,
            String dialogMessage, String initialValue, IInputValidator validator) {
        super(parentShell);
        this.title = dialogTitle;
        message = dialogMessage;
        if (initialValue == null) {
