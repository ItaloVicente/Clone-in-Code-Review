	private void handleNullRefPlaceHolders(MUIElement element, MWindow refWin) {
		List<MPlaceholder> nullRefList = ((ModelServiceImpl) modelService).getNullRefPlaceHolders(element, refWin);

		List<MPart> partList = modelService.findElements(element, null, MPart.class, null);
		for (MPart part : partList) {
			if (CompatibilityPart.COMPATIBILITY_VIEW_URI.equals(part.getContributionURI())
					&& part.getIconURI() == null) {
				part.getTransientData().put(IPresentationEngine.OVERRIDE_ICON_IMAGE_KEY,
						ImageDescriptor.getMissingImageDescriptor().createImage());
			}
		}

		if (nullRefList != null && nullRefList.size() > 0) {
			for (MPlaceholder ph : nullRefList) {
				replacePlaceholder(ph);
			}
		}
	}

	private void replacePlaceholder(MPlaceholder ph) {
		MPart part = modelService.createModelElement(MPart.class);
		part.setElementId(ph.getElementId());
		part.getTransientData().put(IPresentationEngine.OVERRIDE_ICON_IMAGE_KEY,
				ImageDescriptor.getMissingImageDescriptor().createImage());
		String label = (String) ph.getTransientData().get(IWorkbenchConstants.TAG_LABEL);
		if (label != null) {
			part.setLabel(label);
		} else {
			part.setLabel(getLabel(ph.getElementId()));
		}
		part.setContributionURI(CompatibilityPart.COMPATIBILITY_VIEW_URI);
		part.setCloseable(true);
		MElementContainer<MUIElement> curParent = ph.getParent();
		int curIndex = curParent.getChildren().indexOf(ph);
		curParent.getChildren().remove(curIndex);
		curParent.getChildren().add(curIndex, part);
		if (curParent.getSelectedElement() == ph) {
			curParent.setSelectedElement(part);
		}
	}

	private String getLabel(String str) {
		int index = str.lastIndexOf('.');
		if (index == -1)
			return str;
		return str.substring(index + 1);
	}
