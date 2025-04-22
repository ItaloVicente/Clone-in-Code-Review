	default void onAllCSSPropertiesApplyed(Object element, CSSEngine engine) throws Exception {
	}

	default void onAllCSSPropertiesApplyed(Object element, CSSEngine engine, String pseudo) throws Exception {
		onAllCSSPropertiesApplyed(element, engine);
	}
