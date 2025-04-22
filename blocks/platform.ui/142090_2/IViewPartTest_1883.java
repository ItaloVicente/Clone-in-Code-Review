		assertTrue(history.verifyOrder(new String[] { "setInitializationData", "init",
				"createPartControl", "setFocus", "isSaveOnCloseNeeded",
				"widgetDisposed", "toolbarContributionItemWidgetDisposed",
				"toolbarContributionItemDisposed", "dispose" }));
		assertFalse(history.contains("doSave"));
	}
