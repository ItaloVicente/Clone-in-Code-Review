        }
    }

    /**
     *	Remove the passed listener from self's collection of clients
     *	that listen for changes to element checked states
     *
     *	@param listener ICheckStateListener
     */
    public void removeCheckStateListener(ICheckStateListener listener) {
        removeListenerObject(listener);
    }

    /**
     *	Handle the selection of an item in the tree viewer
     *
     *	@param event SelectionChangedEvent
     */
    @Override
