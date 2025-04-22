        exception.printStackTrace();
        event.detail = DND.DROP_NONE;
    }

    /**
     * Performs any work associated with the drop.
     * <p>
     * Subclasses must implement this method to provide drop behavior.
     * </p>
     *
     * @param data the drop data
     * @return <code>true</code> if the drop was successful, and
     *   <code>false</code> otherwise
     */
    public abstract boolean performDrop(Object data);

	/**
	 * Overrides the current operation for a drop that happens immediately
	 * after the current validateDrop.
