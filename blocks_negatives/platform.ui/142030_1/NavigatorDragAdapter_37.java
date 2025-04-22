    private static final String CHECK_MOVE_TITLE = ResourceNavigatorMessages.DragAdapter_title;

    private static final String CHECK_DELETE_MESSAGE = ResourceNavigatorMessages.DragAdapter_checkDeleteMessage;

    ISelectionProvider selectionProvider;

    private TransferData lastDataType;

    /**
     * Constructs a new drag adapter.
     * @param provider The selection provider
     */
    public NavigatorDragAdapter(ISelectionProvider provider) {
        selectionProvider = provider;
    }

    /**
     * This implementation of {@link DragSourceListener#dragFinished(DragSourceEvent)}
     * responds to a drag that has moved resources outside the Navigator by deleting
     * the corresponding source resource.
     */
    @Override
