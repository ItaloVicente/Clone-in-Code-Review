	@Override
	public boolean isPartOrPlaceholderInPerspective(String elementId, MPerspective perspective) {
		List<MPart> findElements = modelService.findElements(perspective, elementId, MPart.class, null);
		if (!findElements.isEmpty()) {
			MPart part = findElements.get(0);

			if (workbenchWindow.getSharedElements().contains(part)) {
				List<MPlaceholder> placeholders = modelService.findElements(perspective, elementId,
						MPlaceholder.class, null);
				for (MPlaceholder mPlaceholder : placeholders) {
					if (mPlaceholder.isVisible() && mPlaceholder.isToBeRendered()) {
						return true;
					}
				}
				return false;
			}
			return part.isVisible() && part.isToBeRendered();
		}
		return false;
	}

