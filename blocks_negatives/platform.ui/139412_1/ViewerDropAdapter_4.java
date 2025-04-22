    	currentEvent = event;
    	if (!validateDrop(currentTarget, event.detail, event.currentDataType)) {
            currentOperation = event.detail = DND.DROP_NONE;
        }
    	currentEvent = null;
    }

    /**
     * Returns the bounds of the given SWT tree or table item.
     *
     * @param item the SWT Item
     * @return the bounds, or <code>null</code> if it is not a known type of item
     */
    protected Rectangle getBounds(Item item) {
        if (item instanceof TreeItem) {
            return ((TreeItem) item).getBounds();
        }
        if (item instanceof TableItem) {
            return ((TableItem) item).getBounds(0);
        }
        return null;
    }

    /**
     * Returns a constant describing the position of the mouse relative to the
     * target (before, on, or after the target.
     *
     * @return one of the <code>LOCATION_* </code> constants defined in this type
     */
    protected int getCurrentLocation() {
        return currentLocation;
    }

    /**
     * Returns the current operation.
     *
     * @return a <code>DROP_*</code> constant from class <code>DND</code>
     *
     * @see DND#DROP_COPY
     * @see DND#DROP_MOVE
     * @see DND#DROP_LINK
     * @see DND#DROP_NONE
     */
    protected int getCurrentOperation() {
        return currentOperation;
    }

    /**
     * Returns the target object currently under the mouse.
     *
     * @return the current target object
     */
    protected Object getCurrentTarget() {
        return currentTarget;
    }

    /**
     * Returns the current {@link DropTargetEvent}.
     *
     * This may be called only inside of the {@link #validateDrop(Object, int, TransferData)}
     * or {@link #performDrop(Object)} methods.
     * @return the DropTargetEvent
     * @since 3.5
     */
    protected DropTargetEvent getCurrentEvent() {
    	Assert.isTrue(currentEvent != null);
    	return currentEvent;
    }

    /**
     * Returns whether visible insertion feedback should be presented to the user.
     * <p>
     * Typical insertion feedback is the horizontal insertion bars that appear
     * between adjacent items while dragging.
     * </p>
     *
     * @return <code>true</code> if visual feedback is desired, and <code>false</code> if not
     */
    public boolean getFeedbackEnabled() {
        return feedbackEnabled;
    }
