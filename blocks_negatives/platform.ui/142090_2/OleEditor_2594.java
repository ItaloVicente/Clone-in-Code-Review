        IWorkbenchWindow window = getSite().getWorkbenchWindow();

        for (int i = 0; i < menuBar.getItemCount(); i++) {
            MenuItem item = menuBar.getItem(i);
            if (item.getData() instanceof IMenuManager)
                id = ((IMenuManager) item.getData()).getId();
            if (id.equals(IWorkbenchActionConstants.M_FILE))
                fileMenu[0] = item;
            else if (id.equals(IWorkbenchActionConstants.M_WINDOW))
                windowMenu[0] = item;
            else {
                if (window.isApplicationMenu(id)) {
                    containerItems.addElement(item);
                }
            }
        }
        MenuItem[] containerMenu = new MenuItem[containerItems.size()];
        containerItems.copyInto(containerMenu);
        clientFrame.setFileMenus(fileMenu);
        clientFrame.setContainerMenus(containerMenu);
        clientFrame.setWindowMenus(windowMenu);
    }

    @Override
