            }
        };
        DragSource dragSource = new DragSource(
                viewer.getControl(), operations);
        dragSource.setTransfer(transferTypes);
        dragSource.addDragListener(listener);
    }

    /**
     * The user is attempting to drag.  Add the appropriate
     * data to the event.
     * @param event The event sent from the drag and drop support.
     */
    void performDragSetData(DragSourceEvent event) {
        IStructuredSelection selection = (IStructuredSelection) viewer
                .getSelection();
        if (selection.isEmpty()) {
