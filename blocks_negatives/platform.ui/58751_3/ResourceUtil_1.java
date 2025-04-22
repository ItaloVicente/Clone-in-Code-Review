		if (element == null) {
			return null;
		}

		if (element instanceof ResourceMapping) {
			return (ResourceMapping) element;
		}

		Object adapter = getAdapter(element, ResourceMapping.class, true);
		if (adapter instanceof ResourceMapping) {
			return (ResourceMapping) adapter;
		}
		return null;
