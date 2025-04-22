    /**
     * Notifies this action delegate that the selection in the workbench has changed.
     * <p>
     * Implementers can use this opportunity to change the availability of the
     * action or to modify other presentation properties.
     * </p><p>
     * When the selection changes, the action enablement state is updated based on
     * the criteria specified in the plugin.xml file. Then the delegate is notified
     * of the selection change regardless of whether the enablement criteria in the
     * plugin.xml file is met.
     * </p>
     *
     * @param action the action proxy that handles presentation portion of
     * 		the action
     * @param selection the current selection, or <code>null</code> if there
     * 		is no selection.
     */
    public void selectionChanged(IAction action, ISelection selection);
