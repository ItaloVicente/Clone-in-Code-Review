	private int lastValidOperation = DND.DROP_NONE;

	public NavigatorDropAdapter(StructuredViewer viewer) {
		super(viewer);
	}

	@Override
	public void dragEnter(DropTargetEvent event) {
		if (FileTransfer.getInstance().isSupportedType(event.currentDataType) && event.detail == DND.DROP_DEFAULT) {
			event.detail = DND.DROP_COPY;
		}
		super.dragEnter(event);
	}
