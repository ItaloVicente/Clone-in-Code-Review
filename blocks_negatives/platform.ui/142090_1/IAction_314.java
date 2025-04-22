    /**
     * <p>
     * Sets the accelerator keycode that this action maps to. This is a bitwise OR
     * of zero or more SWT key modifier masks (i.e. SWT.CTRL or SWT.ALT) and a
     * character code. For example, for Ctrl+Z, use <code>SWT.CTRL | 'Z'</code>.
     * Use 0 for no accelerator.
     * </p>
     * <p>
     * This method should no longer be used for actions in the Eclipse workbench.
     * <code>IWorkbenchCommandSupport</code> and
     * <code>IWorkbenchContextSupport</code> provide all the functionality
     * required for key bindings. If you set an accelerator using this method, then
     * it will not work in the workbench if it conflicts any existing key binding,
     * or if there is a different key binding defined for this action's definition
     * id. The definition id should be used instead -- referring to the command in
     * the workbench from which the key binding should be retrieved.
     * </p>
     *
     * @param keycode
     *            the keycode to be accepted.
     */
