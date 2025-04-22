				MToolBarElement element = createToolControlAddition(item);
				toolBarContribution.getChildren().add(element);
			} else if (IWorkbenchRegistryConstants.TAG_DYNAMIC.equals(itemType)) {
				ContextFunction generator = new ContextFunction() {
					@Override
					public Object compute(IEclipseContext context, String contextKey) {
						ServiceLocator sl = new ServiceLocator();
						sl.setContext(context);
						DynamicToolBarContributionItem dynamicItem = new DynamicToolBarContributionItem(
								MenuHelper.getId(item), sl, item);
						return dynamicItem;
					}
				};

				MToolBarElement element = createToolDynamicAddition(item);
				RenderedElementUtil.setContributionManager(element, generator);
