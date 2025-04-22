						if (txtQuickAccess.isFocusControl()) {
							MPart activePart = partService.getActivePart();
							if (activePart != null) {
								IPresentationEngine pe = activePart.getContext().get(IPresentationEngine.class);
								pe.focusGui(activePart);
							}
						}
