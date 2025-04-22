        restoreAddedWorkingSets();
        restoreChangedWorkingSets();
        restoreRemovedWorkingSets();
        setSelection(null);
        super.cancelPressed();
    }

    /**
     * Overrides method from Window.
     *
     * @see org.eclipse.jface.window.Window#configureShell(Shell)
     */
    @Override
