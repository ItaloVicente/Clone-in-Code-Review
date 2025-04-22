		MElementContainer<MUIElement> parent = part.getParent();
		if (parent != null
				&& parent.getRenderer() == StackRenderer.this) {
			CTabItem cti = findItemForPart(part, parent);
			if (cti != null) {
				updateTab(cti, part, attName, newValue);
			}
			return;
		}

		Set<MPlaceholder> refs = renderedMap.get(part);
		if (refs != null) {
			for (MPlaceholder ref : refs) {
				MElementContainer<MUIElement> refParent = ref
						.getParent();
				if (refParent.getRenderer() instanceof StackRenderer) {
					CTabItem cti = findItemForPart(ref, refParent);
