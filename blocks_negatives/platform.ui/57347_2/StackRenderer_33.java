				MWindow win = modelService.getTopLevelWindowFor(part);
				List<MPlaceholder> refs = modelService.findElements(win, null,
						MPlaceholder.class, null);
				if (refs != null) {
					for (MPlaceholder ref : refs) {
						if (ref.getRef() != part)
							continue;

						MElementContainer<MUIElement> refParent = ref
								.getParent();
						if (refParent != null
								&& refParent.getRenderer() instanceof StackRenderer) {
							CTabItem cti = findItemForPart(ref, refParent);
							if (cti != null) {
								updateTab(cti, part, attName, newValue);
							}
