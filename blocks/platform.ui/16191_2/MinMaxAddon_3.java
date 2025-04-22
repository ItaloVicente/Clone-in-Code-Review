		}
	}


	@Inject
	@Optional
	private void subscribeTopicSelectedElement(
			@UIEventTopic(UIEvents.ElementContainer.TOPIC_SELECTEDELEMENT) Event event) {
		final MUIElement changedElement = (MUIElement) event.getProperty(EventTags.ELEMENT);
		if (!(changedElement instanceof MPerspectiveStack))
			return;

		MPerspectiveStack ps = (MPerspectiveStack) changedElement;
		MWindow window = modelService.getTopLevelWindowFor(ps);
		List<MToolControl> tcList = modelService.findElements(window, null, MToolControl.class,
				null);

		final MPerspective curPersp = ps.getSelectedElement();
		if (curPersp != null) {
			List<String> tags = new ArrayList<String>();
			tags.add(IPresentationEngine.MINIMIZED);

			List<MUIElement> minimizedElements = modelService.findElements(curPersp, null,
					MUIElement.class, tags);
			String perspId = '(' + curPersp.getElementId() + ')';
			for (MUIElement ele : minimizedElements) {
				String fullId = ele.getElementId() + perspId;
