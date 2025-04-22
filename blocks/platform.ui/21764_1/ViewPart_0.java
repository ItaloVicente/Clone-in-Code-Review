	private IPartListener2 partListener = new IPartListener2() {

        public void partVisible(IWorkbenchPartReference partRef) {

        }

        public void partOpened(IWorkbenchPartReference partRef) {

        }

        public void partInputChanged(IWorkbenchPartReference partRef) {

        }

        public void partHidden(IWorkbenchPartReference partRef) {

        }

        public void partDeactivated(IWorkbenchPartReference partRef) {

        }

        public void partClosed(IWorkbenchPartReference partRef) {
            if (partRef instanceof ViewReference) {
                ((ViewReference) partRef).persist();
            }
        }

        public void partBroughtToTop(IWorkbenchPartReference partRef) {

        }

        public void partActivated(IWorkbenchPartReference partRef) {

        }
    };

    IWorkbenchPage page;


    @Override
    public void createPartControl(Composite parent) {
        IWorkbenchPartSite site = getSite();
        if (site != null) {
            IWorkbenchPage page = site.getPage();
            if (page != null) {
                this.page = page;
                page.addPartListener(partListener);
            }
        }
    }
	

