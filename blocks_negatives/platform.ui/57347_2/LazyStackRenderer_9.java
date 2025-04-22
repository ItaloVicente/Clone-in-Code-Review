	private EventHandler lazyLoader = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
			Object element = event.getProperty(UIEvents.EventTags.ELEMENT);

			if (!(element instanceof MGenericStack<?>))
				return;

			@SuppressWarnings("unchecked")
			MGenericStack<MUIElement> stack = (MGenericStack<MUIElement>) element;
			if (stack.getRenderer() != LazyStackRenderer.this)
				return;
			LazyStackRenderer lsr = (LazyStackRenderer) stack.getRenderer();

			MUIElement oldSel = (MUIElement) event
					.getProperty(UIEvents.EventTags.OLD_VALUE);
			if (oldSel != null) {
				hideElementRecursive(oldSel);
			}
