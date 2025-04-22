			Set<MPlaceholder> refs = renderedMap.get(part);
			if (refs != null) {
				for (MPlaceholder ref : refs) {
					MElementContainer<MUIElement> refParent = ref
							.getParent();
					if (refParent.getRenderer() instanceof StackRenderer) {
						CTabItem cti2 = findItemForPart(ref, refParent);
						if (cti2 != null) {
							updateTab(cti2, part, attName, newValue);
