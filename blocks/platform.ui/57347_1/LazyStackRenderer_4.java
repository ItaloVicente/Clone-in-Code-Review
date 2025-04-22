	private EventHandler lazyLoader = event -> {
		Object element = event.getProperty(UIEvents.EventTags.ELEMENT);

		if (!(element instanceof MGenericStack<?>))
			return;

		@SuppressWarnings("unchecked")
		MGenericStack<MUIElement> stack = (MGenericStack<MUIElement>) element;
		if (stack.getRenderer() != LazyStackRenderer.this)
			return;
		LazyStackRenderer lsr = (LazyStackRenderer) stack.getRenderer();
