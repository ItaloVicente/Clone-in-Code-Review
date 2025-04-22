						MElementContainer<MPartSashContainerElement> container = containers.get(0);
						MPlaceholder placeholder = providedPart.getCurSharedRef();
						if (placeholder == null) {
							container.getChildren().add(providedPart);
						} else {
							container.getChildren().add(placeholder);
						}
