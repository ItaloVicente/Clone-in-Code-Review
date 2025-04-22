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
				WorkbenchPlugin.log("Could not open intro", new Status(IStatus.ERROR, WorkbenchPlugin.PI_WORKBENCH, //$NON-NLS-1$
						IStatus.ERROR, "Could not open intro", e)); //$NON-NLS-1$
			}
		}
		setIntroStandby(introPart, standby);
		return introPart;
	}

		ViewIntroAdapterPart viewPart = getViewIntroAdapterPart();
		if (viewPart == null) {
