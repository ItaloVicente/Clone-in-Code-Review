    /**
     * The drop has successfully completed. This event is forwarded to the current
     * drag listener.
     * Doesn't update the current listener, since the current listener  is already the one
     * that completed the drag operation.
     *
     * @param event the drag source event
     * @see DragSourceListener#dragFinished(DragSourceEvent)
     */
    @Override
