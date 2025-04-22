	public void test_1_1_RelationshipInMenuManagerRenderer_Bug552361() throws Exception {

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

			String testId = "org.eclipse.ui.tests.menus.bug552361";

			MPart modelPart = problemsView.getSite().getService(MPart.class);
			IRendererFactory factory = modelPart.getContext().get(IRendererFactory.class);
			MenuManagerRenderer renderer = (MenuManagerRenderer) factory
					.getRenderer(MenuFactoryImpl.eINSTANCE.createPopupMenu(), null);

			MenuManager manager1 = new MenuManager();
			MenuManager manager2 = new MenuManager();

			MMenu menuModel1 = renderer.getMenuModel(manager1);
			MMenu menuModel2 = renderer.getMenuModel(manager2);

			assertNull(menuModel1);
			assertNull(menuModel2);

			popupMenuExtender1 = new PopupMenuExtender(testId, manager1, null, problemsView, null, false);
			popupMenuExtender2 = new PopupMenuExtender(testId, manager2, null, problemsView, null, false);

			menuModel1 = renderer.getMenuModel(manager1);
			menuModel2 = renderer.getMenuModel(manager2);

			assertNotNull(menuModel1);
			assertNotNull(menuModel2);
			assertSame(manager1, renderer.getManager(menuModel1));
			assertSame(manager2, renderer.getManager(menuModel2));

		} finally {
			if (popupMenuExtender1 != null) {
				popupMenuExtender1.dispose();
			}
			if (popupMenuExtender2 != null) {
				popupMenuExtender2.dispose();
			}
		}
	}

