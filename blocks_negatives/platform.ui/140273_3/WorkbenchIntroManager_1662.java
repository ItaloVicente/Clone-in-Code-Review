        ViewIntroAdapterPart viewPart = getViewIntroAdapterPart();
        if (viewPart == null) {
            createIntro(preferredWindow);
        } else {
            try {
                IWorkbenchPage page = viewPart.getSite().getPage();
                IWorkbenchWindow window = page.getWorkbenchWindow();
                if (!window.equals(preferredWindow)) {
                    window.getShell().setActive();
                }

                page.showView(IIntroConstants.INTRO_VIEW_ID);
            } catch (PartInitException e) {
                WorkbenchPlugin
                        .log(
                                "Could not open intro", new Status(IStatus.ERROR, WorkbenchPlugin.PI_WORKBENCH, IStatus.ERROR, "Could not open intro", e)); //$NON-NLS-1$ //$NON-NLS-2$
            }
        }
        setIntroStandby(introPart, standby);
        return introPart;
    }

    /**
     * @param testWindow the window to test
     * @return whether the intro exists in the given window
     */
    /*package*/boolean isIntroInWindow(IWorkbenchWindow testWindow) {
        ViewIntroAdapterPart viewPart = getViewIntroAdapterPart();
        if (viewPart == null) {
