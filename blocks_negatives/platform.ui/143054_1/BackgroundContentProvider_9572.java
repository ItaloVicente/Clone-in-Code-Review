        		continue;
        	}

        	if (!changeQueue.isEmpty()) {
        		dirty = true;
	        	ChangeQueue.Change next = changeQueue.dequeue();

	        	switch(next.getType()) {
		        	case ChangeQueue.ADD: {
		            	filteredAdd(collection, next.getElements(), f);
		        		break;
		        	}
		        	case ChangeQueue.REMOVE: {
		        		Object[] toRemove = next.getElements();

		                flush(toRemove, collection);
		                collection.removeAll(toRemove);

		        		break;
		        	}
		        	case ChangeQueue.UPDATE: {
		        		Object[] items  = next.getElements();

	        	        for (Object item : items) {
	        	            if (collection.contains(item)) {
	        	                collection.remove(item);
	        	                collection.add(item);
	        	                updator.clear(item);
	        	            }
	        	        }

		        		break;
		        	}
		        	case ChangeQueue.SET: {
		        		Object[] items = next.getElements();
		        		collection.clear();
		        		filteredAdd(collection, items, f);

		        		break;
		        	}
	        	}

	        	continue;
        	}

	        int totalElements = collection.size();
            if (limit != -1) {
                if (totalElements > limit) {
                    totalElements = limit;
                }
            }

            if (totalElements != prevSize) {
            	prevSize = totalElements;
		        updator.setTotalItems(totalElements);
		        dirty = true;
            }

            if (!dirty) {
            	break;
            }

            try {
            	ConcurrentTableUpdator.Range updateRange = updator.getVisibleRange();
            	sortMon = new FastProgressReporter();
            	range = updateRange;
            	int sortStart = updateRange.start;
            	int sortLength = updateRange.length;

		        if (limit != -1) {
		            collection.retainFirst(limit, sortMon);
		        }

		        sortLength = Math.min(sortLength, totalElements - sortStart);
		        sortLength = Math.max(sortLength, 0);

		        Object[] objectsOfInterest = new Object[sortLength];

		        collection.getRange(objectsOfInterest, sortStart, true, sortMon);

		        for (int i = 0; i < sortLength; i++) {
