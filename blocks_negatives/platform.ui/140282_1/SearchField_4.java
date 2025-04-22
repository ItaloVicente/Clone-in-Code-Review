					/*
					 * By design, attempting to activate a part that is already
					 * active does not change the focus. However in the case of
					 * using Quick Access, focus is not in the active part, so
					 * re-activating the active part results in focus being left
					 * behind in the text field. If this happens then assign
					 * focus to the active part explicitly.
					 */
					if (txtQuickAccess.isFocusControl()) {
						MPart activePart = partService.getActivePart();
						if (activePart != null) {
							IPresentationEngine pe = activePart.getContext().get(
									IPresentationEngine.class);
							pe.focusGui(activePart);
