    /**
     * A drag operation has started.
     * Forwards this event to each listener. A listener must set <code>event.doit</code>
     * to <code>false</code> if it cannot handle the drag operation. If a listener can
     * handle the drag, it is added to the list of active listeners.
     * The drag is aborted if there are no listeners that can handle it.
     *
     * @param event the drag source event
     * @see DragSourceListener#dragStart(DragSourceEvent)
     */
    @Override
