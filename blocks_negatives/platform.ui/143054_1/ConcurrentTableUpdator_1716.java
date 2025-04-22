	        }
        }
    }


    /**
     * Called in the UI thread by a SetData callback. Refreshes the
     * table if necessary. Returns true iff a refresh is needed.
     * @param includeIndex the index that should be included in the visible range.
     */
    public void checkVisibleRange(int includeIndex) {
        int start = Math.min(table.getTopIndex() - 1, includeIndex);
        int length = Math.max(table.getVisibleItemCount(), includeIndex - start);
        Range r = lastRange;

    	if (start != r.start || length != r.length) {
    		updateTable();
    	}
    }

    /**
     * Updates the table. Sends any unsent items in the visible range to the table,
     * and clears any previously-visible items that have not yet been sent to the table.
     * Must be called from the UI thread.
     */
    private void updateTable() {

        synchronized(this) {

	        if (sentObjects.length != knownObjects.length) {
	        	Object[] newSentObjects = new Object[knownObjects.length];
	        	System.arraycopy(newSentObjects, 0, sentObjects, 0,
	        			Math.min(newSentObjects.length, sentObjects.length));
	        	sentObjects = newSentObjects;
	            table.setItemCount(newSentObjects.length);
	        }

	        int start = Math.min(table.getTopIndex(), knownObjects.length);
	        int length = Math.min(table.getVisibleItemCount(), knownObjects.length - start);
	        int itemCount = table.getItemCount();

        	int oldStart = lastRange.start;
        	int oldLen = lastRange.length;

        	lastRange = new Range(start, length);
