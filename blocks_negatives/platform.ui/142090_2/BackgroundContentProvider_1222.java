        while(true) {
        	if (order != sortOrder) {
        		dirty = true;
        		order = sortOrder;
        		LazySortedCollection newCollection = new LazySortedCollection(order);

        		Object[] items = collection.getItems(false);
        		for (int j = 0; j < items.length && order == sortOrder; j++) {
