        Composite dialogAreaComposite = (Composite) super
                .createDialogArea(parent);
        setToggleButton(createToggleButton(dialogAreaComposite));
        return dialogAreaComposite;
    }

    /**
     * Creates a toggle button without any text or state.  The text and state
     * will be created by <code>createDialogArea</code>.
     *
     * @param parent
     *            The composite in which the toggle button should be placed;
     *            must not be <code>null</code>.
     * @return The added toggle button; never <code>null</code>.
     */
    protected Button createToggleButton(Composite parent) {
        final Button button = new Button(parent, SWT.CHECK | SWT.LEFT);

        GridData data = new GridData(SWT.NONE);
        data.horizontalSpan = 2;
        button.setLayoutData(data);
        button.setFont(parent.getFont());

        button.addSelectionListener(widgetSelectedAdapter(e -> toggleState = button.getSelection()));

        return button;
    }

    /**
     * Returns the toggle button.
     *
     * @return the toggle button
     */
    protected Button getToggleButton() {
        return toggleButton;
    }

    /**
     * An accessor for the current preference store for this dialog.
     *
     * @return The preference store; this value may be <code>null</code> if no
     *         preference is being used.
     */
    public IPreferenceStore getPrefStore() {
        return prefStore;
    }

    /**
     * An accessor for the current key of the toggle preference.
     *
     * @return The preference key; this value may be <code>null</code> if no
     *         preference is being used.
     */
    public String getPrefKey() {
        return prefKey;
    }

    /**
     * Returns the toggle state. This can be called even after the dialog is
     * closed.
     *
     * @return <code>true</code> if the toggle button is checked,
     *         <code>false</code> if not
     */
    public boolean getToggleState() {
        return toggleState;
    }

    /**
     * A mutator for the key of the preference to be modified by the toggle
     * button.
     *
     * @param prefKey
     *            The prefKey to set. If this value is <code>null</code>,
     *            then no preference will be modified.
     */
    public void setPrefKey(String prefKey) {
        this.prefKey = prefKey;
    }

    /**
     * A mutator for the preference store to be modified by the toggle button.
     *
     * @param prefStore
     *            The prefStore to set. If this value is <code>null</code>,
     *            then no preference will be modified.
     */
    public void setPrefStore(IPreferenceStore prefStore) {
        this.prefStore = prefStore;
    }

    /**
     * A mutator for the button providing the toggle option. If the button
     * exists, then it will automatically get the text set to the current toggle
     * message, and its selection state set to the current selection state.
     *
     * @param button
     *            The button to use; must not be <code>null</code>.
     */
    protected void setToggleButton(Button button) {
        if (button == null) {
            throw new NullPointerException(

        if (!button.isDisposed()) {
            final String text;
            if (toggleMessage == null) {
                text = JFaceResources
            } else {
                text = toggleMessage;
            }
            button.setText(text);
            button.setSelection(toggleState);
        }

        this.toggleButton = button;
    }

    /**
     * A mutator for the text on the toggle button. The button will
     * automatically get updated with the new text, if it exists.
     *
     * @param message
     *            The new text of the toggle button; if it is <code>null</code>,
     *            then used the default toggle message.
     */
    protected void setToggleMessage(String message) {
        this.toggleMessage = message;

        if ((toggleButton != null) && (!toggleButton.isDisposed())) {
            final String text;
            if (toggleMessage == null) {
                text = JFaceResources
            } else {
                text = toggleMessage;
            }
            toggleButton.setText(text);
        }
    }

    /**
     * A mutator for the state of the toggle button. This method will update the
     * button, if it exists.
     *
     * @param toggleState
     *            The desired state of the toggle button (<code>true</code>
     *            means the toggle should be selected).
     */
    public void setToggleState(boolean toggleState) {
        this.toggleState = toggleState;

        if ((toggleButton != null) && (!toggleButton.isDisposed())) {
            toggleButton.setSelection(toggleState);
        }
    }

    /**
