    /**
     * An accessor for the workbench window associated with this state. This
     * should never be <code>null</code>, as the setting follows the last
     * workbench window to have focus.
     *
     * @return The workbench window to which the key binding architecture is
     *         currently attached; should never be <code>null</code>.
     */
    IWorkbenchWindow getAssociatedWindow() {
        return associatedWindow;
    }
