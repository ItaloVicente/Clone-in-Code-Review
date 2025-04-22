
				MPart activePart = partService.getActivePart();
				if (curActive != activePart && activePart != null) {
					engine.focusGui(activePart);
				} else if (currentFocus != null && !currentFocus.isDisposed()) {
					currentFocus.forceFocus();
				}
