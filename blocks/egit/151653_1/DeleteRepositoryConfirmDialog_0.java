			projectsViewer
					.setLabelProvider(new DecoratingStyledCellLabelProvider(
							new WorkbenchLabelProvider(),
							PlatformUI.getWorkbench().getDecoratorManager()
									.getLabelDecorator(),
							null));
