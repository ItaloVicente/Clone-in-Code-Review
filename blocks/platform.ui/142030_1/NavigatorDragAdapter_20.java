	private static final String CHECK_MOVE_TITLE = ResourceNavigatorMessages.DragAdapter_title;

	private static final String CHECK_DELETE_MESSAGE = ResourceNavigatorMessages.DragAdapter_checkDeleteMessage;

	ISelectionProvider selectionProvider;

	private TransferData lastDataType;

	public NavigatorDragAdapter(ISelectionProvider provider) {
		selectionProvider = provider;
	}

	@Override
