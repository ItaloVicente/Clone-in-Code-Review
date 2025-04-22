    /**
     *	An item was checked in one of self's two views.  Determine which
     *	view this occurred in and delegate appropriately
     *
     *	@param event CheckStateChangedEvent
     */
    public void checkStateChanged(final CheckStateChangedEvent event) {

        BusyIndicator.showWhile(treeViewer.getControl().getDisplay(),
                new Runnable() {
                    public void run() {
                        if (event.getCheckable().equals(treeViewer)) {
							treeItemChecked(event.getElement(), event
                                    .getChecked());
						} else {
							listItemChecked(event.getElement(), event
                                    .getChecked(), true);
						}

                        notifyCheckStateChangeListeners(event);
                    }
                });
    }

