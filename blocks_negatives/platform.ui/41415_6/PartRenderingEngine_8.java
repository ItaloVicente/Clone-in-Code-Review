					} else {
						if (renderer != null && added.isToBeRendered())
							renderer.childRendered(changedElement, added);
					}

					int newLocation = modelService.getElementLocation(added);
					if (newLocation == EModelService.IN_SHARED_AREA
							|| newLocation == EModelService.OUTSIDE_PERSPECTIVE) {
						MWindow topWin = modelService
								.getTopLevelWindowFor(added);
						modelService.hideLocalPlaceholders(topWin, null);
