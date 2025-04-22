			if (secondaryId && getActivePart() != null) {
				MElementContainer<MUIElement> parent = getActivePart().getParent();
				if (getActivePart().getCurSharedRef() != null) {
					parent = getActivePart().getCurSharedRef().getParent();
				}
				while (!(MPerspective.class.isInstance(parent) || MWindow.class.isInstance(parent))) {
					parent = parent.getParent();
				}

				List<MPlaceholder> phs = modelService.findElements(parent, descId, MPlaceholder.class, null);
				if (phs.size() == 1) {
					MPlaceholder ph = phs.get(0);
					sharedPart.setCloseable(ph.isCloseable());
					sharedPart.getTags().addAll(ph.getTags());
				}
			}
