        final IShellProvider provider = navigator.getSite();
        openProjectAction = new OpenResourceAction(provider);
        closeProjectAction = new CloseResourceAction(provider);
        closeUnrelatedProjectsAction = new CloseUnrelatedProjectsAction(provider);
        refreshAction = new RefreshAction(provider) {
        	@Override
