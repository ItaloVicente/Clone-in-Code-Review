	interface StyleSheetChangeListener extends EventListener {
		void styleSheetAdded(StyleSheet styleSheet);

		void styleSheetRemoved(StyleSheet styleSheet);
	}

	default void addStyleSheetChangeListener(StyleSheetChangeListener listener) {
	}

	default void removeStyleSheetChangeListener(StyleSheetChangeListener listener) {
	}
