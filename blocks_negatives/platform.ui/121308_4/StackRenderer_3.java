				MenuItem menuItemAll = new MenuItem(menu, SWT.NONE);
				menuItemAll.setText(SWTRenderersMessages.menuCloseAll);
				menuItemAll.addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> {
					MPart part1 = (MPart) menu.getData(STACK_SELECTED_PART);
					closeSiblingParts(part1, false);
				}));
