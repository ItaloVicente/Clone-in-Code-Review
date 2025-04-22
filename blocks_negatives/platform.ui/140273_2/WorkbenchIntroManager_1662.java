        return null;
    }

    /**
     * @return a new IIntroPart.  This has the side effect of setting the introPart field to the new
     * value.
     */
    /*package*/IIntroPart createNewIntroPart() throws CoreException {
        IntroDescriptor introDescriptor = workbench.getIntroDescriptor();
		introPart = introDescriptor == null ? null
                : introDescriptor.createIntro();
        if (introPart != null) {
        	workbench.getExtensionTracker().registerObject(
					introDescriptor.getConfigurationElement()
							.getDeclaringExtension(), introPart,
