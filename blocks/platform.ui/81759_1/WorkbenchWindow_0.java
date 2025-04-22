	@Inject
	private void listenToPerspectiveSaved(
			@Optional @UIEventTopic(UIEvents.UILifeCycle.PERSPECTIVE_SAVED) Event event) {
		if(event==null){
			return;
		}
		Object changed = event.getProperty(UIEvents.EventTags.ELEMENT);
		if (changed != null && changed instanceof MPerspective) {
			String perspectiveId = ((MPerspective) changed).getElementId();
			IPerspectiveDescriptor pDesc = workbench.getPerspectiveRegistry().findPerspectiveWithId(perspectiveId);
			firePerspectiveSavedAs(page, null, pDesc);
		}
	}

