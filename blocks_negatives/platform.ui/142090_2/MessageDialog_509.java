        return buttons[index];
    }

    /**
     * Returns the minimum message area width in pixels This determines the
     * minimum width of the dialog.
     * <p>
     * Subclasses may override.
     * </p>
     *
     * @return the minimum message area width (in pixels)
     */
    protected int getMinimumMessageWidth() {
        return convertHorizontalDLUsToPixels(IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH);
    }

    /**
     * Handle the shell close. Set the return code to <code>SWT.DEFAULT</code>
     * as there has been no explicit close by the user.
     *
     * @see org.eclipse.jface.window.Window#handleShellCloseEvent()
     */
    @Override
