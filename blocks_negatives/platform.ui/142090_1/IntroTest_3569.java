    }

    /**
     * Open the intro, change perspective, close the intro
     * and ensure that the intro has not been closed in the
     * other perspective.
     * See bug 174213
     * See IntroTest2.java
     */
    public void testPerspectiveChangeWith32StickyBehavior() {
    	IWorkbench workbench = window.getWorkbench();
        IIntroPart part = workbench.getIntroManager().showIntro(window, false);
        assertNotNull(part);
        IWorkbenchPage activePage = window.getActivePage();
        IPerspectiveDescriptor oldDesc = activePage.getPerspective();
        activePage.setPerspective(WorkbenchPlugin.getDefault()
                .getPerspectiveRegistry().findPerspectiveWithId(
                        "org.eclipse.ui.tests.api.SessionPerspective"));

        IViewPart viewPart = window.getActivePage().findView(
