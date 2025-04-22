	private void handleWidgetSelection(Event event) {
		if (widget != null && !widget.isDisposed()) {
			if (dropdownEvent(event)) {
				return;
			}
			if (model.getType() == ItemType.CHECK
					|| model.getType() == ItemType.RADIO) {
				boolean selection = false;
				if (widget instanceof MenuItem) {
					selection = ((MenuItem) widget).getSelection();
				} else if (widget instanceof ToolItem) {
					selection = ((ToolItem) widget).getSelection();
				}
				model.setSelected(selection);
			}
			if (canExecuteItem(event)) {
				executeItem(event);
			}
		}
	}

	private boolean dropdownEvent(Event event) {
		if (event.detail == SWT.ARROW && model instanceof MToolItem) {
			ToolItem ti = (ToolItem) event.widget;
			MMenu mmenu = ((MToolItem) model).getMenu();
			if (mmenu == null) {
				return false;
			}
			Menu menu = getMenu(mmenu, ti);
			if (menu == null) {
				return true;
			}
			Rectangle itemBounds = ti.getBounds();
			Point displayAt = ti.getParent().toDisplay(itemBounds.x,
					itemBounds.y + itemBounds.height);
			menu.setLocation(displayAt);
			menu.setVisible(true);

			Display display = menu.getDisplay();
			while (menu.isVisible()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
			return true;
		}
		return false;
	}

	protected Menu getMenu(final MMenu mmenu, ToolItem toolItem) {
		Object obj = mmenu.getWidget();
		if (obj instanceof Menu && !((Menu) obj).isDisposed()) {
			return (Menu) obj;
		}
		if (RenderedElementUtil.isRenderedMenu(mmenu)) {
			obj = RenderedElementUtil.getContributionManager(mmenu);
			if (obj instanceof IContextFunction) {
				final IEclipseContext lclContext = getContext(mmenu);
				obj = ((IContextFunction) obj).compute(lclContext, null);
				RenderedElementUtil.setContributionManager(mmenu, obj);
			}
			if (obj instanceof IMenuCreator) {
				final IMenuCreator creator = (IMenuCreator) obj;
				final Menu menu = creator.getMenu(toolItem.getParent()
						.getShell());
				if (menu != null) {
					toolItem.addDisposeListener(new DisposeListener() {
						@Override
						public void widgetDisposed(DisposeEvent e) {
							if (menu != null && !menu.isDisposed()) {
								creator.dispose();
								mmenu.setWidget(null);
							}
						}
					});
					menu.setData(AbstractPartRenderer.OWNING_ME, menu);
					return menu;
				}
			}
		} else {
			final IEclipseContext lclContext = getContext(model);
			IPresentationEngine engine = lclContext
					.get(IPresentationEngine.class);
			obj = engine.createGui(mmenu, toolItem.getParent(), lclContext);
			if (obj instanceof Menu) {
				return (Menu) obj;
			}
			if (logger != null) {
			}
		}
		return null;
	}

