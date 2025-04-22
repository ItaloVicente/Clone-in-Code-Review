
		if (MPlaceholder.class.isInstance(element)
				&& MPlaceholder.class.cast(element).getRef().getTags()
						.contains(MAXIMIZEABLE_CHILDREN_TAG)) {
			Set<MUIElement> toRemove = new LinkedHashSet<MUIElement>();
			for (MUIElement maxElement : curMax) {
				if (modelService.find(maxElement.getElementId(), element) != null) {
					toRemove.add(maxElement);
				}
			}
			curMax.removeAll(toRemove);
		}

