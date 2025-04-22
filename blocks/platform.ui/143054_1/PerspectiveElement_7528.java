		String label = descriptor.getLabel();
		String description = descriptor.getDescription();
		if (description != null && !description.isEmpty()) {
			return label + separator + description;
		}
		return label;
