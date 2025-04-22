        initDragAndDrop();
        makeActions();

        menuMgr.add(copyAction);
        menuMgr.add(new Separator());
        menuMgr.add(defaultsAction);
        Menu menu = menuMgr.createContextMenu(viewer.getControl());
        viewer.getControl().setMenu(menu);

        viewer.getControl().addHelpListener(new HelpListener() {
            /*
             * @see HelpListener#helpRequested(HelpEvent)
             */
            @Override
