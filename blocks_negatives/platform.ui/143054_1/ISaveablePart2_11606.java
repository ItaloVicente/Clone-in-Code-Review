    /**
     * Prompts the user for input on what to do with unsaved data.
     * This method is only called when the part is closed or when
     * the Workbench is shutting down.
     * <p>
     * Implementors are expected to open a custom dialog where the
     * user will be able to determine what to do with the unsaved data.
     * Implementors may also return a result of <code>DEFAULT</code>
     * to get the default prompt handling from the Workbench.
     * </p>
     *
	 * @return the return code, must be either <code>YES</code>,
	 * <code>NO</code>, <code>CANCEL</code> or <code>DEFAULT</code>.
