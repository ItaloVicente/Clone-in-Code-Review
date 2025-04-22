				}
			}
			return accessibleProjects.toArray();
		} else if (element instanceof IContainer) {
			IContainer container = (IContainer) element;
			if (container.isAccessible()) {
				try {
