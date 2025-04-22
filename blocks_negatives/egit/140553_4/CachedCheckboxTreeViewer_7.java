		for (int i = 0; i < elements.length; i++) {
			Object[] children = contentProvider != null ? contentProvider.getChildren(elements[i]) : null;
			if (!getGrayed(elements[i]) && (children == null || children.length == 0)) {
				if (!checkState.contains(elements[i])) {
					checkState.add(elements[i]);
				}
			}
