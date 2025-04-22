		else {
			Object value = element.getTransientData().get(IPresentationEngine.RENDERING_PARENT_KEY);
			if (value != null) {
				return value;
			}
		}
