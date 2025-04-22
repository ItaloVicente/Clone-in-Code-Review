            fileToolBar
                    .add(new GroupMarker(IWorkbenchActionConstants.SAVE_EXT));
            fileToolBar.add(getPrintItem());
            fileToolBar
                    .add(new GroupMarker(IWorkbenchActionConstants.PRINT_EXT));

            fileToolBar
                    .add(new Separator(IWorkbenchActionConstants.BUILD_GROUP));
            fileToolBar
                    .add(new GroupMarker(IWorkbenchActionConstants.BUILD_EXT));
            fileToolBar.add(new Separator(
                    IWorkbenchActionConstants.MB_ADDITIONS));
