
					IToolBarManager toolbarManager = ((IToolBarContributionItem) item).getToolBarManager();
					if (toolbarManager instanceof ToolBarManager) {
						renderer.clearModelToManager(child, (ToolBarManager) toolbarManager);
					}

