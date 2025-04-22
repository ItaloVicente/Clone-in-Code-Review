			IContributionManager parentManager = actionSet.getBars().getMenuManager();
			while (parentManager instanceof SubContributionManager) {
				parentManager = ((SubContributionManager) parentManager).getParent();

				if (parentManager instanceof MenuManager) {
					IEclipseContext context = (IEclipseContext) window
							.getService(IEclipseContext.class);
					EModelService modelService = context.get(EModelService.class);
					IRendererFactory rendererFactory = context.get(IRendererFactory.class);
					MenuManagerRenderer mr = (MenuManagerRenderer) rendererFactory.getRenderer(
							modelService.createModelElement(MMenu.class), null);
					MMenu parent = mr.getMenuModel((MenuManager) parentManager);
					if (parent != null) {
						mr.reconcileManagerToModel((MenuManager) parentManager, parent);
						break;
					}
				}
			}

