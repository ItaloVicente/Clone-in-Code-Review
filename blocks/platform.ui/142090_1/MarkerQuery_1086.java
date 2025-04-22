		} catch (CoreException e) {
			Policy.handle(e);
			return null;
		}

		String[] values = new String[attributes.length];
		for (int i = 0; i < attributes.length; i++) {
			try {
				Object value = marker.getAttribute(attributes[i]);
				if (value == null) {
