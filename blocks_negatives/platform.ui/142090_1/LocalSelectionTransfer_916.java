        Object result = super.nativeToJava(transferData);
        if (result != null && isInvalidNativeType(result)) {
            Policy.getLog().log(new Status(
                            IStatus.ERROR,
                            Policy.JFACE,
                            IStatus.ERROR,
                            JFaceResources.getString("LocalSelectionTransfer.errorMessage"), null)); //$NON-NLS-1$
        }
        return selection;
    }

    /**
     * Sets the transfer data for local use.
     *
     * @param s the transfer data
     */
    public void setSelection(ISelection s) {
        selection = s;
    }

    /**
     * Returns the time when the selection operation
     * this transfer is associated with was started.
     *
     * @return the time when the selection operation has started
     *
     * @see org.eclipse.swt.events.TypedEvent#time
     */
    public long getSelectionSetTime() {
        return selectionSetTime;
    }

    /**
