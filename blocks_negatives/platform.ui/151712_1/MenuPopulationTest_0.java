	public void testMenuServicePopupReuse_Bug549818() throws Exception {

		PopupMenuExtender popupMenuExtender1 = null;
		PopupMenuExtender popupMenuExtender2 = null;
		try {

			window.getActivePage().showView(IPageLayout.ID_PROBLEM_VIEW);

			processEventsUntil(new Condition() {
				@Override
				public boolean compute() {
					return window.getActivePage().getActivePart() != null;
				}
			}, 10000);

			IWorkbenchPart problemsView = window.getActivePage().getActivePart();
			assertNotNull(problemsView);

			List<MMenu> menus = problemsView.getSite().getService(MPart.class).getMenus();
			int before = menus.size();

			String testId = "org.eclipse.ui.tests.menus.bug549818";
			MenuManager manager1 = new MenuManager();
			MenuManager manager2 = new MenuManager();

			popupMenuExtender1 = new PopupMenuExtender(testId, manager1, null, problemsView, null, false);
			popupMenuExtender2 = new PopupMenuExtender(testId, manager2, null, problemsView, null, false);

			assertEquals(before + 1, menus.size());

		} finally {
			if (popupMenuExtender1 != null) {
				popupMenuExtender1.dispose();
			}
			if (popupMenuExtender2 != null) {
				popupMenuExtender2.dispose();
			}
		}
	}

