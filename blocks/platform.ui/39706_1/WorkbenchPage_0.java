	private void addVisibleReferences(MPerspective perspective, Collection<MPart> parts, ViewReference reference,
			Collection<IViewReference> visibleReferences) {
		for (MPart part : parts) {
			if (reference.getModel().getElementId().equals(part.getElementId())
					&& (isStickyView(reference.getModel().getElementId()) || partService
							.isPartOrPlaceholderInPerspective(part.getElementId(), perspective))) {
				visibleReferences.add(reference);
				return;
			}
		}
	}

