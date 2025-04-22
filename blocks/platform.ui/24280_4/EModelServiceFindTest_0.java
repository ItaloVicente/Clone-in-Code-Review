	private MHandler findHandler(EModelService ms,
			MApplicationElement searchRoot, final String id) {
		if (searchRoot == null || id == null)
			return null;

		List<MHandler> handlers = ms.findElements(searchRoot, MHandler.class,
				EModelService.ANYWHERE, new Selector() {
					@Override
					public boolean select(MApplicationElement element) {
						return element instanceof MHandler
								&& id.equals(element.getElementId());
					}
				});
		if (handlers.size() > 0) {
			return handlers.get(0);
		}
		return null;
	}

