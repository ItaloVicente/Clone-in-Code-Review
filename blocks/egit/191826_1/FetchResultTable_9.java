				Object[] children = super.getChildren(element);
				if (children != null) {
					return children;
				}
				if (loader == null) {
					return new Object[0];
