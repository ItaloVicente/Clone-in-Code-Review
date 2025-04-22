	@Inject
	@Optional
	private void subscribeTopicElement(@UIEventTopic(UIEvents.EventTags.ELEMENT) Event event) {
		Object element = event.getProperty(UIEvents.EventTags.ELEMENT);

		if (!(element instanceof MGenericStack<?>))
			return;

		MGenericStack<MUIElement> stack = (MGenericStack<MUIElement>) element;
		if (stack.getRenderer() != LazyStackRenderer.this)
			return;
		LazyStackRenderer lsr = (LazyStackRenderer) stack.getRenderer();

		MUIElement oldSel = (MUIElement) event.getProperty(UIEvents.EventTags.OLD_VALUE);
		if (oldSel != null) {
			hideElementRecursive(oldSel);
