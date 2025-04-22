			MPlaceholder eaPlaceholder = (MPlaceholder) modelService.find(ID_EDITOR_AREA,
					persp == null ? win : persp);
			if (element != eaPlaceholder && eaPlaceholder != null
					&& eaPlaceholder.getWidget() != null && eaPlaceholder.isVisible()) {
				elementsToMinimize.add(eaPlaceholder);
			}
		}

		{
			List<MPlaceholder> areas = modelService.findElements(persp == null ? win : persp,
					ID_EDITOR_AREA, MPlaceholder.class, null, EModelService.ANYWHERE);
			boolean foundRelevantArea = false;
			for (MPlaceholder placeholder : areas) {
				if (placeholder == element)
					continue;
				if (win != getWindowFor(placeholder))
					continue;
				if (modelService.find(element.getElementId(), placeholder) == null)
					continue;
				if (placeholder.getRef().getTags().contains(MAXIMIZEABLE_CHILDREN_TAG))
					foundRelevantArea = true;
				List<MPartStack> partStacks = modelService.findElements(placeholder, null,
						MPartStack.class, null);
				for (MPartStack partStack : partStacks) {
					if (partStack == element)
						continue;
					elementsToMinimize.add(partStack);
