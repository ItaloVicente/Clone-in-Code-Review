			if (foundRelevantArea) {
				List<MUIElement> elementsToRemove = new ArrayList<MUIElement>();
				for (MUIElement element2 : elementsToMinimize) {
					List<Object> findElements = modelService.findElements(element2,
							element.getElementId(), null, null);
					if (findElements != null && findElements.size() != 0)
						elementsToRemove.add(element2);
				}
				elementsToMinimize.removeAll(elementsToRemove);
			}
