        pathVariablesGroup.dispose();
        linkedResourceEditor.dispose();
        super.dispose();
    }

    /**
     * Empty implementation. This page does not use the workbench.
     * @param workbench
     *
     * @see IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     */
    public void init(IWorkbench workbench) {
    }

    /**
     * Commits the temporary state to the path variable manager in response to user
     * confirmation.
     *
     * @see PreferencePage#performOk()
     * @see PathVariablesGroup#performOk()
     */
    @Override
