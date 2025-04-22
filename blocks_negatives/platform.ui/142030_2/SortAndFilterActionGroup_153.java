        IMenuManager menu = actionBars.getMenuManager();
        IMenuManager submenu = new MenuManager(ResourceNavigatorMessages.ResourceNavigator_sort);
        menu.add(submenu);
        submenu.add(sortByNameAction);
        submenu.add(sortByTypeAction);
        menu.add(filterAction);
    }
