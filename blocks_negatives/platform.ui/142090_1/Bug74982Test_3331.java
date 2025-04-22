    /**
     * Tests that the <code>SelectAllHandler</code> triggers a selection
     * event. Creates a dialog with a text widget, gives the text widget focus,
     * and then calls the select all command. This should then call the
     * <code>SelectAllHandler</code> and trigger a selection event.
     *
     * @throws ExecutionException
     *             If the <code>SelectAllHandler</code> is broken in some way.
     * @throws NotHandledException
     *             If the dialog does not have focus, or if the
     *             <code>WorkbenchCommandSupport</code> class is broken in
     *             some way.
     */
    public final void testSelectAllHandlerSendsSelectionEvent()
            throws ExecutionException, NotHandledException {
        final Shell dialog = new Shell(fWorkbench.getActiveWorkbenchWindow()
                .getShell());
        dialog.setLayout(new GridLayout());
        final Text text = new Text(dialog, SWT.SINGLE);
        text.setText("Mooooooooooooooooooooooooooooo");
        text.setLayoutData(new GridData());
        text.addSelectionListener(new SelectionAdapter() {
            @Override
