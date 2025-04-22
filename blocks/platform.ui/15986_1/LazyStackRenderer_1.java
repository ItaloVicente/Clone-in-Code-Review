				final MUIElement oldUiElement = oldSel instanceof MPlaceholder ? ((MPlaceholder) oldSel)
						.getRef() : oldSel;
				if (oldUiElement instanceof MPart) {
					PartLifeCycleState.transitionPartState(
							(MPart) oldUiElement, PartLifeCycleState.HIDDEN);
				}
