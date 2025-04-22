	private static void clearMenuServiceContributions(PartSite site, MPart part) {
		IMenuService menuService = site.getService(IMenuService.class);
		if (!(menuService instanceof WorkbenchMenuService)) {
			return;
		}
		WorkbenchMenuService service = (WorkbenchMenuService) menuService;
		service.clearContributions(site, part);
	}

	public static void clearOpaqueToolBarItems(ToolBarManagerRenderer tbmr, MToolBar toolbar) {
