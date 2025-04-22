		if (fMemento.getInteger(P_GROUP_BY).intValue() == GROUP_BY_NONE) {
			elements.subList(0, elements.size() - limit).clear();
		} else {
			List copy = new ArrayList(entriesCount);
		    for (AbstractEntry group : elements) {
			copy.addAll(Arrays.asList(group.getChildren(group)));
		    }
