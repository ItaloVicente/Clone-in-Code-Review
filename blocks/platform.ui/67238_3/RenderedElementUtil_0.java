	public static MToolControl createRenderedToolBarElement() {
		final MToolControl object = MMenuFactory.INSTANCE.createToolControl();
		object.getTags().add(RENDERED_TAG);
		return object;
	}

