    /**
     *
     * Create a list selection dialog with a possible Yes/No or Cancel result.
     *
     * @param parentShell
     * @param input
     * @param contentProvider
     * @param labelProvider
     * @param message
     * @deprecated see class comment
     */
    @Deprecated
	public YesNoCancelListSelectionDialog(
            org.eclipse.swt.widgets.Shell parentShell,
            Object input,
            org.eclipse.jface.viewers.IStructuredContentProvider contentProvider,
            org.eclipse.jface.viewers.ILabelProvider labelProvider,
            String message) {
        super(parentShell, input, contentProvider, labelProvider, message);
    }
