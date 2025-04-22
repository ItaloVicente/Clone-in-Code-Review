			ExpressionInfo info = new ExpressionInfo();
			record.collectInfo(info);
			updateVariables.addAll(Arrays.asList(info.getAccessedVariableNames()));
			final IEclipseContext parentContext = getContext(toolbarModel);
			parentContext.runAndTrack(new RunAndTrack() {
				@Override
				public boolean changed(IEclipseContext context) {
					if (getManager(toolbarModel) == null) {
						return false;
					}

					record.updateVisibility(parentContext.getActiveLeaf());
					runExternalCode(() -> {
						manager.update(false);
						getUpdater().updateContributionItems(e -> {
							if (e instanceof MToolBarElement) {
								if (((MUIElement) ((MToolBarElement) e).getParent()) == toolbarModel) {
									return true;
								}
							}
							return false;
						});
					});
					return true;
				}
			});
