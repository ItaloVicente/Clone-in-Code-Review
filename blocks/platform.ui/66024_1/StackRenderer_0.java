	private abstract class AbstractItemUpdaterHandler implements EventHandler {

		@Override
		public void handleEvent(Event event) {
			MUIElement element = (MUIElement) event.getProperty(UIEvents.EventTags.ELEMENT);
			if (!(element instanceof MPart))
				return;

			MPart part = (MPart) element;

			String attName = (String) event.getProperty(UIEvents.EventTags.ATTNAME);
			Object newValue = event.getProperty(UIEvents.EventTags.NEW_VALUE);

			if (element.getParent() != null && element.getParent().getRenderer() == StackRenderer.this) {
				CTabItem cti = findItemForPart(element, element.getParent());
				if (cti != null) {
					updateTab(cti, part, attName, newValue);
				}
				return;
			}

			Collection<MPlaceholder> refs = findPlaceHolder(element);
			if (refs != null) {
				for (MPlaceholder ref : refs) {
					if (ref.getRef() != part)
						continue;

					MElementContainer<MUIElement> refParent = ref.getParent();
					if (refParent != null && refParent.getRenderer() instanceof StackRenderer) {
						CTabItem cti = findItemForPart(ref, refParent);
						if (cti != null) {
							updateTab(cti, part, attName, newValue);
						}
					}
				}
			}
		}

		protected abstract Collection<MPlaceholder> findPlaceHolder(MUIElement element);
	}

