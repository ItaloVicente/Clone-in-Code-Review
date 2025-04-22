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
						CTabItem cti2 = findItemForPart(ref, refParent);
						if (cti2 != null) {
							updateTab(cti2, part, attName, newValue);
