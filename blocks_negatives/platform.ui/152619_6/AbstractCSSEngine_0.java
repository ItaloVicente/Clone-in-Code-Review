		if (handlers2 != null) {
			for (ICSSPropertyHandler2 handler2 : handlers2) {
				try {
					handler2.onAllCSSPropertiesApplyed(element, this, pseudo);
				} catch (Exception e) {
					handleExceptions(e);
				}
