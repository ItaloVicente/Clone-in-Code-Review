        menu.add(new Separator());

        menu.add(closeAction);
        menu.add(closeAllAction);
        menu.add(new GroupMarker(IWorkbenchActionConstants.CLOSE_EXT));
        menu.add(new Separator());
        menu.add(saveAction);
        menu.add(saveAsAction);
        menu.add(saveAllAction);
        menu.add(getRevertItem());
        menu.add(new Separator());
        menu.add(getMoveItem());
        menu.add(getRenameItem());
        menu.add(getRefreshItem());

        menu.add(new GroupMarker(IWorkbenchActionConstants.SAVE_EXT));
        menu.add(new Separator());
        menu.add(getPrintItem());
        menu.add(new GroupMarker(IWorkbenchActionConstants.PRINT_EXT));
        menu.add(new Separator());
        menu.add(new GroupMarker(IWorkbenchActionConstants.OPEN_EXT));
        menu.add(new Separator());
        menu.add(importResourcesAction);
        menu.add(exportResourcesAction);
        menu.add(new GroupMarker(IWorkbenchActionConstants.IMPORT_EXT));
        menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));

        menu.add(new Separator());
        menu.add(getPropertiesItem());

        menu.add(new Separator());
