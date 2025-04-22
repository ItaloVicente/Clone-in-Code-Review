    	MarkElement[] topLevel = getToc(file);
    	AdaptableList list = new AdaptableList();
    	for (MarkElement topLevel1 : topLevel) {
    		addSections(list, topLevel1);
    	}
    	return list;
