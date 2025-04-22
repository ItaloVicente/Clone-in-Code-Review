    /**
     * Create the widget for the user dialog preference.
     *
     * @param composite
     */
    protected void createShowUserDialogPref(Composite composite) {
        showUserDialogButton = new Button(composite, SWT.CHECK);
        showUserDialogButton.setText(WorkbenchMessages.WorkbenchPreference_RunInBackgroundButton);
        showUserDialogButton.setToolTipText(WorkbenchMessages.WorkbenchPreference_RunInBackgroundToolTip);
        showUserDialogButton.setSelection(WorkbenchPlugin.getDefault()
                .getPreferenceStore().getBoolean(
                        IPreferenceConstants.RUN_IN_BACKGROUND));
    }

    /**
     * Create the widget for the heap status preference.
     *
     * @param composite
     */
    protected void createHeapStatusPref(Composite composite) {
        showHeapStatusButton = new Button(composite, SWT.CHECK);
        showHeapStatusButton.setText(WorkbenchMessages.WorkbenchPreference_HeapStatusButton);
        showHeapStatusButton.setToolTipText(WorkbenchMessages.WorkbenchPreference_HeapStatusButtonToolTip);

        showHeapStatusButton.setSelection(PrefUtil.getAPIPreferenceStore().getBoolean(
                        IWorkbenchPreferenceConstants.SHOW_MEMORY_MONITOR));
    }

    /**
     * Creates the composite which will contain all the preference controls for
     * this page.
     *
     * @param parent
     *            the parent composite
     * @return the composite for this page
     */
    protected Composite createComposite(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        composite.setLayout(layout);
        composite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL
                | GridData.HORIZONTAL_ALIGN_FILL));
        return composite;
    }

    protected void createStickyCyclePref(Composite composite) {
        stickyCycleButton = new Button(composite, SWT.CHECK);
        stickyCycleButton.setText(WorkbenchMessages.WorkbenchPreference_stickyCycleButton);
        stickyCycleButton.setSelection(getPreferenceStore().getBoolean(
                IPreferenceConstants.STICKY_CYCLE));
    }
