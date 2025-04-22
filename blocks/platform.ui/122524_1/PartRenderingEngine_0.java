		List<String> tags = element.getTags();
		if (element instanceof MPart && tags.contains(TAG_EDITOR)) {
			tags.stream().filter(x -> !x.equals(TAG_EDITOR)).forEach(x -> builder.append(" ").append(x));
			String label = ((MPart) element).getLabel();
			if (label != null) {
				builder.append(" (").append(label).append(") ");
			}
		}
