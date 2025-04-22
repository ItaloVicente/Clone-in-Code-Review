			if (IWorkbenchRegistryConstants.TAG_COMMAND.equals(itemType)) {
				MToolBarElement element = createToolBarCommandAddition(child);
				toolBarContribution.getChildren().add(element);
			} else if (IWorkbenchRegistryConstants.TAG_SEPARATOR.equals(itemType)) {
				MToolBarElement element = createToolBarSeparatorAddition(child);
				toolBarContribution.getChildren().add(element);
			} else if (IWorkbenchRegistryConstants.TAG_CONTROL.equals(itemType)) {
				MToolBarElement element = createToolControlAddition(child);
				toolBarContribution.getChildren().add(element);
			} else if (IWorkbenchRegistryConstants.TAG_DYNAMIC.equals(itemType)) {
				ContextFunction generator = new ContextFunction() {
					@Override
					public Object compute(IEclipseContext context, String contextKey) {
						ServiceLocator sl = new ServiceLocator();
						sl.setContext(context);
						DynamicToolBarContributionItem dynamicItem = new DynamicToolBarContributionItem(
								MenuHelper.getId(child), sl, child);
						return dynamicItem;
					}
				};

				MToolBarElement element = createToolDynamicAddition(child);
				RenderedElementUtil.setContributionManager(element, generator);
				toolBarContribution.getChildren().add(element);
			}
