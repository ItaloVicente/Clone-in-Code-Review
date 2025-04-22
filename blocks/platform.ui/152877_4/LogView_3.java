		synchronized (elements) {
			if (fMemento.getInteger(P_GROUP_BY).intValue() == GROUP_BY_NONE) {
				elements.subList(0, elements.size() - limit).clear();
			} else {
				List<AbstractEntry> copy = new ArrayList<>(entriesCount);
				for (AbstractEntry group : elements) {
					List<AbstractEntry> children = Arrays.asList(group.getChildren(group));
					copy.addAll(children);
				}
