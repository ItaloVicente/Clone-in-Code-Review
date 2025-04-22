				MHandledToolItem toolItem = MenuHelper.createToolItem(application, (CommandContributionItem) item);
				if (toolItem == null) {
					continue;
				}
				toolItem.setRenderer(renderer);
				HandledContributionItem ci = ContextInjectionFactory.make(HandledContributionItem.class,
						window.getContext());
				if (manager instanceof ContributionManager) {
					ContributionManager cm = (ContributionManager) manager;
					cm.insert(index, ci);
					cm.remove(item);
