			MPart active = getActivePart();
			if (secondaryId && active != null) {
				MElementContainer<MUIElement> parent = active.getParent();
				MPlaceholder sharedRef = active.getCurSharedRef();
				if (sharedRef != null) {
					parent = sharedRef.getParent();
				}
				while (!(MPerspective.class.isInstance(parent) || MWindow.class.isInstance(parent))) {
					if (parent.getParent() == null) {
						break;
					}
					parent = parent.getParent();
				}

				List<MPlaceholder> phs = modelService.findElements(parent, descId, MPlaceholder.class, null);
				if (phs.size() == 1) {
					MPlaceholder ph = phs.get(0);
					sharedPlaceHolderParent = ph.getParent();
					sharedPart.setCloseable(ph.isCloseable());
					sharedPart.getTags().addAll(ph.getTags());
				}
			}
