					IEclipseContext dynamicMenuContext = EclipseContextFactory
							.create();
					dynamicMenuContext.set(MMenu.class, menuModel);
					lifecycleService.validate(PostClose.class, menuModel,
							dynamicMenuContext);
					dynamicMenuContext.dispose();
