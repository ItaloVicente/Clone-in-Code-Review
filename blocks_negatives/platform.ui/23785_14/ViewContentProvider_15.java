        Object[] children = childMap.get(element);
        if (children == null) {
            children = createChildren(element);
            childMap.put(element, children);
        }
        return children;
    }

    /**
	 * Does the actual work of getChildren.
