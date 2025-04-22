				if (ref instanceof MPart) {
					List<MPlaceholder> placeholders = modelService.findElements(window,
							ref.getElementId(), MPlaceholder.class, null,
							EModelService.IN_ANY_PERSPECTIVE | EModelService.IN_SHARED_AREA
									| EModelService.OUTSIDE_PERSPECTIVE);
					for (MPlaceholder placeholder : placeholders) {
						if (placeholder.getRef() == ref && placeholder.isToBeRendered()) {
							return;
						}
