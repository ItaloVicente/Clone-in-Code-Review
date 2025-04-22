	public List<MPart> getActivationList() {
		return Collections.unmodifiableList(activationList);
	}

	@Inject
	@Optional
	private void subscribeTopicPartClosed(@EventTopic(UIEvents.UIElement.WIDGET) Event event) {
		Object element = event.getProperty(EventTags.ELEMENT);
		if (UIEvents.isREMOVE(event) && element instanceof MPart && event.getProperty(EventTags.NEW_VALUE) == null) {
			activationList.remove(element);
		}
	}

	@Inject
	@Optional
	private void subscribeTopicPartActivate(@EventTopic(UIEvents.UILifeCycle.ACTIVATE) Event event) {
		Object element = event.getProperty(EventTags.ELEMENT);
		if (element instanceof MPart) {
			MPart part = (MPart) element;
			activationList.remove(part);
			activationList.addFirst(part);
		}
	}

	@Inject
	@Optional
	private void subscribeTopicBringToTop(@EventTopic(UIEvents.UILifeCycle.BRINGTOTOP) Event event) {
		Object element = event.getProperty(EventTags.ELEMENT);
		if (element instanceof MPart) {
			MPart part = (MPart) element;
			MElementContainer<?> parent = part.getParent();
			if (parent == null) {
				MPlaceholder placeholder = part.getCurSharedRef();
				if (placeholder == null) {
					return;
				}

				parent = placeholder.getParent();
			}

			if (parent instanceof MPartStack) {
				int newIndex = lastIndexOfContainer(parent);
				if (newIndex >= 0 && part == activationList.get(newIndex)) {
					return;
				}
				activationList.remove(part);
				if (newIndex >= 0 && newIndex < activationList.size() - 1) {
					activationList.add(newIndex, part);
				} else {
					activationList.add(part);
				}
			}
		}
	}

	private int lastIndexOfContainer(MElementContainer<?> parent) {
		for (int i = 0; i < activationList.size(); i++) {
			MPart mPart = activationList.get(i);
			MElementContainer<MUIElement> container = mPart.getParent();
			if (container == parent) {
				return i;
			} else if (container == null) {
				MPlaceholder placeholder = mPart.getCurSharedRef();
				if (placeholder != null && placeholder.getParent() == parent) {
					return i;
				}
			}
		}
		return -1;
	}

