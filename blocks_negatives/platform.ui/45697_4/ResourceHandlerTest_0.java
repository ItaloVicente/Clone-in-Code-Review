	public void testXPathModelProcessor() {

		URI uri = URI.createPlatformPluginURI("org.eclipse.e4.ui.tests/xmi/modelprocessor/base.e4xmi", true);
		ResourceHandler handler = createHandler(uri);
		Resource resource = handler.loadMostRecentModel();
		MApplication application = (MApplication) resource.getContents().get(0);
		assertNotNull(application);

		/**
		 * We will now test the various ways an element can be contributed to
		 * multiple parents. ModelFragments.e4mi has been configured to add 2
		 * menus to the Main Menu. These menus will receive our test
		 * contributions.
		 */
		MMenu mainMenu = application.getChildren().get(0).getMainMenu();
		assertNotNull(mainMenu);
		MMenu menu1 = (MMenu) findByElementId(mainMenu.getChildren(), "fragment.contributedMenu1");
		assertNotNull(menu1);
		MMenu menu2 = (MMenu) findByElementId(mainMenu.getChildren(), "fragment.contributedMenu2");
		assertNotNull(menu2);
		assertNotNull(findByElementId(menu1.getChildren(), "fragment.contributedMenuItem.csv"));
		assertNotNull(findByElementId(menu2.getChildren(), "fragment.contributedMenuItem.csv"));
		assertNotNull(findByElementId(menu1.getChildren(), "fragment.contributedMenuItem.xpath"));
		assertNotNull(findByElementId(menu2.getChildren(), "fragment.contributedMenuItem.xpath"));
	}

	/**
	 * @param children
	 * @param id
	 * @return the MMenuElement or null if not found
	 */
	private Object findByElementId(List<MMenuElement> children, String id) {
		for (MMenuElement item : children) {
			if (id.equals(item.getElementId())) {
				return item;
			}
		}
		return null;
	}

