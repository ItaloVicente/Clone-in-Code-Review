	protected MMenu getMenuModel(MenuManager menuManager, MenuLocationURI location) {
		if ("org.eclipse.ui.main.menu".equals(location.getPath())) //$NON-NLS-1$
		{
			WorkbenchWindow workbenchWindow = (WorkbenchWindow) getWindow();
			MWindow window = workbenchWindow.getModel();
			return window.getMainMenu();
		}
	
		MMenu mMenu = null;

		Menu menu = menuManager.getMenu();
		final MPart mPart = getPartToExtend(menu == null ? null : menu.getParent());
		if (mPart != null) {
			for (MMenu mMenuIt : mPart.getMenus()) {
				if (mMenuIt.getElementId().equals(menuManager.getId())) {
					mMenu = mMenuIt;
				}
			}
		} else {
			System.err.println("Can not register a MenuManager without a MPart"); //$NON-NLS-1$
		}
		if (mMenu == null) {
			mMenu = MenuFactoryImpl.eINSTANCE.createMenu();
			mMenu.setElementId(menuManager.getId());
			if (mMenu.getElementId() == null) {
				mMenu.setElementId(location.getPath());
			}
			mMenu.getTags().add(ContributionsAnalyzer.MC_MENU);
			mMenu.setLabel(menuManager.getMenuText());
		}

		if (mPart != null) {
			mPart.getMenus().add(mMenu);
		}


	
		IRendererFactory factory = e4Context.get(IRendererFactory.class);
		AbstractPartRenderer obj = factory.getRenderer(mMenu, null);
		if (obj instanceof MenuManagerRenderer) {
			MenuManagerRenderer renderer = (MenuManagerRenderer) obj;
			renderer.linkModelToManager(mMenu, menuManager);
		}
	
		return mMenu;
	}

	private MPart getOwnerPart(Control control) {
		return (MPart) control
				.getData(org.eclipse.e4.ui.internal.workbench.swt.AbstractPartRenderer.OWNING_ME);
	}

	private MPart getPartToExtend(Control control) {
		MPart part = null;
		if (control == null) {
		} else {
			Control currentControl = control;
			MPart ownerPart = getOwnerPart(currentControl);
			while (currentControl != null && ownerPart == null) {
				currentControl = currentControl.getParent();
				ownerPart = getOwnerPart(currentControl);
			}
			part = ownerPart;
		}
		return part;
	}

