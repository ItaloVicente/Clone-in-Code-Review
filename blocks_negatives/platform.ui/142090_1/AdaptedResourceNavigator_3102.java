        viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
        viewer.setUseHashlookup(true);
        viewer.setContentProvider(new TestAdaptableContentProvider());
        IDecoratorManager manager = getSite().getWorkbenchWindow()
                .getWorkbench().getDecoratorManager();
        viewer.setLabelProvider(new DecoratingLabelProvider(
                new TestAdaptableWorkbenchAdapter(), manager
                        .getLabelDecorator()));

        viewer.setInput(getInitialInput());
        updateTitle();

        menuMgr.setRemoveAllWhenShown(true);
