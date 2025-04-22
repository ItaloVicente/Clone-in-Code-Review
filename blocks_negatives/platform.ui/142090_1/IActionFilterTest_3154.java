        assertTrue(filter.getCalled());
    }

    public void testDynamicMenuContribution() throws Throwable {
        testMenu(DYNAMIC_MENU_VIEW_ID);
    }

    public void testStaticMenuContribution() throws Throwable {
        testMenu(STATIC_MENU_VIEW_ID);
    }

    /**
     * Select a ListElement, popup a menu and verify that the
     * ListElementActionFilter.testAttribute method is invoked.
     * Then verify that the correct actions are added to the
     * popup menu.
     *
     * See Setup above.
     */
    private void testMenu(String viewId) throws Throwable {
        ListElement red = new ListElement("red");
        ListElement blue = new ListElement("blue");
        ListElement green = new ListElement("green");
        ListElement redTrue = new ListElement("red", true);

        ListView view = (ListView) fPage.showView(viewId);
        MenuManager menuMgr = view.getMenuManager();
        view.addElement(red);
        view.addElement(blue);
        view.addElement(green);
        view.addElement(redTrue);

        ListElementActionFilter filter = ListElementActionFilter.getSingleton();

        view.selectElement(red);
