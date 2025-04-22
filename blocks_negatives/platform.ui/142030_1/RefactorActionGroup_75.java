        TreeViewer treeViewer = navigator.getViewer();
        IShellProvider provider = navigator.getSite();
        clipboard = new Clipboard(provider.getShell().getDisplay());

        pasteAction = new PasteAction(provider.getShell(), clipboard);
        ISharedImages images = PlatformUI.getWorkbench().getSharedImages();
        pasteAction.setDisabledImageDescriptor(images
                .getImageDescriptor(ISharedImages.IMG_TOOL_PASTE_DISABLED));
        pasteAction.setImageDescriptor(images
                .getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));

        copyAction = new CopyAction(provider.getShell(), clipboard, pasteAction);
        copyAction.setDisabledImageDescriptor(images
                .getImageDescriptor(ISharedImages.IMG_TOOL_COPY_DISABLED));
        copyAction.setImageDescriptor(images
                .getImageDescriptor(ISharedImages.IMG_TOOL_COPY));

        moveAction = new ResourceNavigatorMoveAction(provider.getShell(), treeViewer);
        renameAction = new ResourceNavigatorRenameAction(provider.getShell(), treeViewer);

        deleteAction = new DeleteResourceAction(provider);
        deleteAction.setDisabledImageDescriptor(images
                .getImageDescriptor(ISharedImages.IMG_TOOL_DELETE_DISABLED));
        deleteAction.setImageDescriptor(images
                .getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
    }

    @Override
