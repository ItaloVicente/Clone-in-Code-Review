    	MarkElement[] topLevel = getToc(file);
    	AdaptableList list = new AdaptableList();
    	for (MarkElement t : topLevel) {
    		addSections(list, t);
    	}
    	return list;
