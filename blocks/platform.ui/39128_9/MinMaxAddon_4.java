		if (element instanceof MPartStack) {
			MArea area = getAreaFor((MPartStack) element);
			if (area != null && area.getTags().contains(MAXIMIZEABLE_CHILDREN_TAG)) {
				List<MPartStack> maximizedAreaChildren = modelService.findElements(area, null,
						MPartStack.class, maximizeTag);
				ignoreTagChanges = true;
				try {
					for (MPartStack partStack : maximizedAreaChildren) {
						partStack.getTags().remove(IPresentationEngine.MAXIMIZED);
						adjustCTFButtons(partStack);
					}
				} finally {
					ignoreTagChanges = false;
				}
			}
		}
