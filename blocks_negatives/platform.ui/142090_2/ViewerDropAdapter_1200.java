    protected Object determineTarget(DropTargetEvent event) {
        return event.item == null ? null : event.item.getData();
    }

    private void doDropValidation(DropTargetEvent event) {
    	if (event.detail != DND.DROP_NONE && overrideOperation == -1)
    		lastValidOperation = event.detail;

    	currentOperation = lastValidOperation;
        currentEvent = event;
        overrideOperation = -1;
        if (!validateDrop(currentTarget, currentOperation, event.currentDataType)) {
        	currentOperation = DND.DROP_NONE;
        }

        if (overrideOperation != -1)
        	event.detail = overrideOperation;
        else
        	event.detail = currentOperation;
        currentEvent = null;
    }

    @Override
