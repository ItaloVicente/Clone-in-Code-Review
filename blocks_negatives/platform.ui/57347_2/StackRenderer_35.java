				Set<MPlaceholder> refs = renderedMap.get(part);
				if (refs != null) {
					for (MPlaceholder ref : refs) {
						MElementContainer<MUIElement> refParent = ref
								.getParent();
						if (refParent.getRenderer() instanceof StackRenderer) {
							CTabItem cti = findItemForPart(ref, refParent);
							if (cti != null) {
								updateTab(cti, part, attName, newValue);
							}
