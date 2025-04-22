		for (Widget child : children) {
			if (child.getClass() == clazz) {
				selectedChildren.add(child);
			}
			if (child instanceof Composite) {
				selectedChildren.addAll(getWidgets((Composite) child, clazz));
			}
		}
		return selectedChildren;
	}

	@Override
