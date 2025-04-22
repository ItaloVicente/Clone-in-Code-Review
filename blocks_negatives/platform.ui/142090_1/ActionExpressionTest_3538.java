        super.doSetUp();
        fWindow = openTestWindow();
        fPage = fWindow.getActivePage();
    }

    public void testAllAction() throws Throwable {
        ListView view = showListView();
        MenuManager mgr = getActionMenuManager(view);

        selectAndUpdateMenu(view, null, mgr);
        testAction(mgr, "allAction_v2", false);

        selectAndUpdateMenu(view, red, mgr);
        testAction(mgr, "allAction_v2", true);

        selectAndUpdateMenu(view, blue, mgr);
        testAction(mgr, "allAction_v2", true);

        selectAndUpdateMenu(view, redTrue, mgr);
        testAction(mgr, "allAction_v2", true);
    }

    public void testRedAction() throws Throwable {
        ListView view = showListView();
        MenuManager mgr = getActionMenuManager(view);

        selectAndUpdateMenu(view, null, mgr);
        testAction(mgr, "redAction_v2", false);

        selectAndUpdateMenu(view, red, mgr);
        testAction(mgr, "redAction_v2", true);

        selectAndUpdateMenu(view, blue, mgr);
        testAction(mgr, "redAction_v2", false);

        selectAndUpdateMenu(view, redTrue, mgr);
        testAction(mgr, "redAction_v2", true);
    }

    public void testNotRedAction() throws Throwable {
        ListView view = showListView();
        MenuManager mgr = getActionMenuManager(view);

        selectAndUpdateMenu(view, null, mgr);
        testAction(mgr, "notRedAction_v2", false);

        selectAndUpdateMenu(view, red, mgr);
        testAction(mgr, "notRedAction_v2", false);

        selectAndUpdateMenu(view, blue, mgr);
        testAction(mgr, "notRedAction_v2", true);

        selectAndUpdateMenu(view, redTrue, mgr);
        testAction(mgr, "notRedAction_v2", false);
    }

    public void testTrueAction() throws Throwable {
        ListView view = showListView();
        MenuManager mgr = getActionMenuManager(view);

        selectAndUpdateMenu(view, null, mgr);
        testAction(mgr, "trueAction_v2", false);

        selectAndUpdateMenu(view, red, mgr);
        testAction(mgr, "trueAction_v2", false);

        selectAndUpdateMenu(view, blue, mgr);
        testAction(mgr, "trueAction_v2", false);

        selectAndUpdateMenu(view, redTrue, mgr);
        testAction(mgr, "trueAction_v2", true);
    }

    public void testRedOrBlueAction() throws Throwable {
        ListView view = showListView();
        MenuManager mgr = getActionMenuManager(view);

        selectAndUpdateMenu(view, null, mgr);
        testAction(mgr, "redOrBlueAction_v2", false);

        selectAndUpdateMenu(view, red, mgr);
        testAction(mgr, "redOrBlueAction_v2", true);

        selectAndUpdateMenu(view, blue, mgr);
        testAction(mgr, "redOrBlueAction_v2", true);

        selectAndUpdateMenu(view, redTrue, mgr);
        testAction(mgr, "redOrBlueAction_v2", true);
    }

    public void testRedAndTrueAction() throws Throwable {
        ListView view = showListView();
        MenuManager mgr = getActionMenuManager(view);

        selectAndUpdateMenu(view, null, mgr);
        testAction(mgr, "redAndTrueAction_v2", false);

        selectAndUpdateMenu(view, red, mgr);
        testAction(mgr, "redAndTrueAction_v2", false);

        selectAndUpdateMenu(view, blue, mgr);
        testAction(mgr, "redAndTrueAction_v2", false);

        selectAndUpdateMenu(view, redTrue, mgr);
        testAction(mgr, "redAndTrueAction_v2", true);
    }

    public void testPluginStateActions() throws Throwable {
        ListView view = showListView();
        MenuManager mgr = getActionMenuManager(view);

        selectAndUpdateMenu(view, red, mgr);
        testAction(mgr, "pluginNotInstalledAction_v2", false);
        testAction(mgr, "pluginInstalledAction_v2", true);
        testAction(mgr, "pluginNotActivatedAction_v2", false);
        testAction(mgr, "pluginActivatedAction_v2", true);
    }

    public void testSystemPropertyAction() throws Throwable {
        ListView view = showListView();
        MenuManager mgr = getActionMenuManager(view);

        System.setProperty("ActionExpressionVar", "");
        selectAndUpdateMenu(view, red, mgr);
        testAction(mgr, "systemPropertyAction_v2", false);

        System.setProperty("ActionExpressionVar", "bubba");
        selectAndUpdateMenu(view, blue, mgr);
        testAction(mgr, "systemPropertyAction_v2", true);
    }

    /**
     * Creates the list view.
     */
    protected ListView showListView() throws Throwable {
        ListView view = (ListView) (fPage.showView(VIEW_ID));
        red = new ListElement("red");
        blue = new ListElement("blue");
        redTrue = new ListElement("red", true);
        view.addElement(red);
        view.addElement(blue);
        view.addElement(redTrue);
        return view;
    }

    /**
