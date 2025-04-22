				if (getWindowFor(theStack) != win)
					continue;

				loc = modelService.getElementLocation(theStack);
				if (loc != EModelService.IN_SHARED_AREA && theStack.getWidget() != null
						&& theStack.isVisible() && !theStack.getTags().contains(MINIMIZED)) {
					elementsToMinimize.add(theStack);
				}
			}
