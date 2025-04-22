    /**
     * Creates the context menu
     */
    private void createContextMenu() {
        MenuManager menuMgr = new MenuManager();
        menuMgr.setRemoveAllWhenShown(true);
        menuMgr.addMenuListener(menuMgr1 -> fillMenu(menuMgr1));
        Menu menu = menuMgr.createContextMenu(this);
        setMenu(menu);
    }

    private void fillMenu(IMenuManager menuMgr) {
        menuMgr.add(new SetMarkAction());
        menuMgr.add(new ClearMarkAction());
        menuMgr.add(new ShowMaxAction());
        menuMgr.add(new CloseHeapStatusAction());
