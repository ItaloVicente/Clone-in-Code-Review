		if (MPartStack.class.isInstance(element)) {
			MArea area = getAreaFor(MPartStack.class.cast(element));
			if (area != null && area.getTags().contains(MAXIMIZEABLE_CHILDREN_TAG)) {
				List<MPartStack> maximizedAreaChildren = modelService.findElements(area, null,
						MPartStack.class, maximizeTag);
				ignoreTagChanges = true;
				for (MPartStack partStack : maximizedAreaChildren) {
					partStack.getTags().remove(IPresentationEngine.MAXIMIZED);
					adjustCTFButtons(partStack);
				}
				ignoreTagChanges = false;
			}
		}
