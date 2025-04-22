        }
        return null;
    }

    /**
     * Clear the event state.
     */
    private void clearEventState() {
        eventReceived = false;
        eventPart = null;
        eventSelection = null;
    }

    /*
     * @see ISelectionListener#selectionChanged(IWorkbenchPart, ISelection)
     */
    @Override
