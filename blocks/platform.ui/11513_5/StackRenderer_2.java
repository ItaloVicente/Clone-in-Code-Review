	protected CTabItem findItemForPart(MPart part) {
		if (part.getParent() != null
				&& part.getParent().getRenderer() == StackRenderer.this) {
			CTabItem cti = findItemForPart(part, part.getParent());
			if (cti != null) {
				return cti;
			}
		}

		MWindow win = modelService.getTopLevelWindowFor(part);
		List<MPlaceholder> refs = modelService.findElements(win, null,
				MPlaceholder.class, null);
		if (refs != null) {
			for (MPlaceholder ref : refs) {
				if (ref.getRef() != part)
					continue;

				MElementContainer<MUIElement> refParent = ref.getParent();
				if (refParent != null
						&& refParent.getRenderer() instanceof StackRenderer) {
					CTabItem cti = findItemForPart(ref, refParent);
					if (cti != null) {
						return cti;
					}
				}
			}
		}
		return null;
	}

