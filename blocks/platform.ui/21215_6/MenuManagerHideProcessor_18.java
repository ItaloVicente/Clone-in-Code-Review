					IEclipseContext dynamicMenuContext = EclipseContextFactory
							.create();
					dynamicMenuContext.set(MMenu.class, menuModel);
					lifecycleService.process(PostClose.class, menuModel,
							dynamicMenuContext);
					dynamicMenuContext.dispose();
