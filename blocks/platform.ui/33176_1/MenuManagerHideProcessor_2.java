						IEclipseContext dynamicMenuContext = EclipseContextFactory
								.create();

						ArrayList<MMenuElement> mel = entry.getValue();

						dynamicMenuContext.set(List.class, mel);
						IEclipseContext parentContext = modelService
								.getContainingContext(currentMenuElement);
						ContextInjectionFactory.invoke(contribution,
								AboutToHide.class, parentContext,
								dynamicMenuContext, null);
						dynamicMenuContext.dispose();
						if (mel != null && mel.size() > 0) {
							renderer.removeDynamicMenuContributions(
									menuManager, menuModel, mel);
