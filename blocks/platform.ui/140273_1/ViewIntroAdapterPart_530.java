		super.init(site);
		Workbench workbench = (Workbench) site.getWorkbenchWindow().getWorkbench();
		try {
			introPart = workbench.getWorkbenchIntroManager().createNewIntroPart();
			setPartName(introPart.getTitle());
			introPart.addPropertyListener((source, propId) -> firePropertyChange(propId));
			introSite = new ViewIntroAdapterSite(site, workbench.getIntroDescriptor());
			introPart.init(introSite, memento);

		} catch (CoreException e) {
			WorkbenchPlugin.log(IntroMessages.Intro_could_not_create_proxy, new Status(IStatus.ERROR,
					WorkbenchPlugin.PI_WORKBENCH, IStatus.ERROR, IntroMessages.Intro_could_not_create_proxy, e));
		}
	}

	@Override
