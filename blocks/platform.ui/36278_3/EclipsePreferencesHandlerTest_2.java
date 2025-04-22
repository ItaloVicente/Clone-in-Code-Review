		try {
			handler.applyCSSProperty(element, PREFERENCES_PROP, listValue, null, engine);
		} catch (Exception e) {
			fail("Apply CSSProperty should not throw exception");

		}
