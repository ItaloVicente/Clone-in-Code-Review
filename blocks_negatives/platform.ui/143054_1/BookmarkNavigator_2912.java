            }
        };
        viewer.addDragSupport(operations, transferTypes, listener);
    }

    /**
     * The user is attempting to drag marker data.  Add the appropriate
     * data to the event depending on the transfer type.
     */
    void performDragSetData(DragSourceEvent event) {
        if (MarkerTransfer.getInstance().isSupportedType(event.dataType)) {
