			MPart active = getActivePart();
			if (secondaryId && active != null) {
				MElementContainer<MUIElement> parent = active.getParent();
				MPlaceholder sharedRef = active.getCurSharedRef();
				if (sharedRef != null) {
					parent = sharedRef.getParent();
