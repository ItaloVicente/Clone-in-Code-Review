		e4Context.set(IMenuService.class.getName(), new ContextFunction() {
			@Override
			public Object compute(IEclipseContext context, String contextKey) {
				if (menuService == null) {
					menuService = new SlaveMenuService(context.getParent().get(IMenuService.class),
							model);
				}
				return menuService;
			}
		});
