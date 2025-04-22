				for (MPart part : parts) {
					if (reference.getModel().getElementId().equals(part.getElementId())
							&& (isStickyView(reference.getModel().getElementId()) || partService
									.isPartOrPlaceholderInPerspective(part.getElementId(), perspective))) {
						visibleReferences.add(reference);
					}
				}
