    /**
     * A flag indicating that overwrites should always occur.
     */
    private boolean alwaysOverwrite = false;

    /**
     * The last valid operation.
     */
    private int lastValidOperation = DND.DROP_NONE;

    /**
     * Constructs a new drop adapter.
     *
     * @param viewer the navigator's viewer
     */
    public NavigatorDropAdapter(StructuredViewer viewer) {
        super(viewer);
    }

    /*
     * @see org.eclipse.swt.dnd.DropTargetListener#dragEnter(org.eclipse.swt.dnd.DropTargetEvent)
     */
    @Override
	public void dragEnter(DropTargetEvent event) {
        if (FileTransfer.getInstance().isSupportedType(event.currentDataType)
                && event.detail == DND.DROP_DEFAULT) {
       		event.detail = DND.DROP_COPY;
          }
        super.dragEnter(event);
    }
