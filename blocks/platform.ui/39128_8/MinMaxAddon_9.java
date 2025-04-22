		{
			List<MPlaceholder> areas = modelService.findElements(persp == null ? win : persp,
					ID_EDITOR_AREA, MPlaceholder.class, null, EModelService.PRESENTATION);

			for (MPlaceholder placeholder : areas) {
				if (placeholder == element)
					continue;
				if (win != getWindowFor(placeholder))
					continue;
				if (!placeholder.getRef().getTags().contains(MAXIMIZEABLE_CHILDREN_TAG))
					continue;
				List<MPartStack> partStacks = modelService.findElements(placeholder, null,
						MPartStack.class, null);
				if (partStacks.contains(element))
					continue;
				for (MPartStack partStack : partStacks) {
					elementsToRestore.remove(partStack);
				}

			}

		}

