	@SuppressWarnings("unchecked")
	private MToolBar findToolbar(String id) {
		for (Object elem: modelService.findElements(window, id, MTrimElement.class,
				Collections.EMPTY_LIST)) {
			if (elem instanceof MToolBar) {
				return (MToolBar) elem;
			}
		}
		return null;
	}

