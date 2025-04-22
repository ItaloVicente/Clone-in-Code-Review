					ToolBar innerToolBar = toolBarManager.getControl();
					if (innerToolBar != null) {
						innerToolBar.setMenu(null);
						Menu innerParentMenu = innerToolBar.getParent().getMenu();
						if (innerParentMenu != null) {
							innerParentMenu.removeListener(SWT.Hide, this);
						}
					}
				}
			});
		}
	}

	private void handleWidgetDispose(DisposeEvent event) {
		coolItem = null;
	}

	@Override
