    private void add(Object[] toAdd) {
    	changeQueue.enqueue(ChangeQueue.ADD, toAdd);
    	makeDirty();
    }

    /**
     * Called with the complete contents of the model
     *
     * @param contents new contents of the model
     */
    private void setContents(Object[] contents) {
    	changeQueue.enqueue(ChangeQueue.SET, contents);
    	makeDirty();
    }

    /**
     * Called when elements are removed from the model
     *
     * @param toRemove elements removed from the model
     */
    private void remove(Object[] toRemove) {
        changeQueue.enqueue(ChangeQueue.REMOVE, toRemove);
        makeDirty();
        refresh();
    }

    /**
     * Notifies the updator that the given elements have changed
     *
     * @param toFlush changed elements
     * @param collection collection of currently-known elements
     */
    private void flush(Object[] toFlush, LazySortedCollection collection) {
        for (Object item : toFlush) {
            if (collection.contains(item)) {
                updator.clear(item);
            }
        }
    }


    /**
     * Called when elements in the model change
     *
     * @param items changed items
     */
    private void update(Object[] items) {
        changeQueue.enqueue(ChangeQueue.UPDATE, items);
        makeDirty();
    }
