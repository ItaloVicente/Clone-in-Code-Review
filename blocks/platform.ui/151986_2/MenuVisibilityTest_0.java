	public void testMenuManagerVisibilityAndEnablement() {
		IContributionItem item = new ContributionItem() {
		};
		final MenuManager parentMenuManager = new MenuManager("parentMenu");
		final MenuManager subMenuManager = new MenuManager("subMenu");
		AbstractContributionFactory factory = new AbstractContributionFactory(LOCATION, TestPlugin.PLUGIN_ID) {
			@Override
			public void createContributionItems(IServiceLocator menuService, IContributionRoot additions) {
				additions.addContributionItem(item, null);
			}
		};
		parentMenuManager.add(subMenuManager);

		menuService.addContributionFactory(factory);
		menuService.populateContributionManager(subMenuManager, LOCATION);

		Shell shell = window.getShell();

		final Menu menuBar = parentMenuManager.createContextMenu(shell);
		Event e = new Event();
		e.type = SWT.Show;
		e.widget = menuBar;

		{
			item.setVisible(true);
			subMenuManager.setVisible(true);

			parentMenuManager.updateAll(true);
			menuBar.notifyListeners(SWT.Show, e);

			assertTrue(subMenuManager.isVisible());
			assertTrue(subMenuManager.isEnabled());
			assertEquals(1, parentMenuManager.getMenu().getItemCount());
			MenuItem subMenuItem = parentMenuManager.getMenu().getItem(0);
			assertEquals(subMenuManager.getMenu(), subMenuItem.getMenu());
			assertTrue(subMenuItem.isEnabled());
		}

		{
			item.setVisible(false);
			subMenuManager.setVisible(true);

			parentMenuManager.updateAll(true);
			menuBar.notifyListeners(SWT.Show, e);

			assertFalse(subMenuManager.isVisible());
			assertTrue(subMenuManager.isEnabled()); // always true
			assertEquals(0, parentMenuManager.getMenu().getItemCount());
		}

		{
			item.setVisible(true);
			subMenuManager.setVisible(true);

			parentMenuManager.updateAll(true);
			menuBar.notifyListeners(SWT.Show, e);

			assertTrue(subMenuManager.isVisible());
			assertTrue(subMenuManager.isEnabled());
			assertEquals(1, parentMenuManager.getMenu().getItemCount());
			MenuItem subMenuItem = parentMenuManager.getMenu().getItem(0);
			assertEquals(subMenuManager.getMenu(), subMenuItem.getMenu());
			assertTrue(subMenuItem.isEnabled());
		}

		{
			item.setVisible(true);
			subMenuManager.setVisible(false);

			parentMenuManager.updateAll(true);
			menuBar.notifyListeners(SWT.Show, e);

			assertFalse(subMenuManager.isVisible());
			assertTrue(subMenuManager.isEnabled()); // always true
			assertEquals(0, parentMenuManager.getMenu().getItemCount());
		}

		menuService.releaseContributions(parentMenuManager);
		menuService.removeContributionFactory(factory);
		parentMenuManager.dispose();
	}

