		return null;
	}

		IntroDescriptor introDescriptor = workbench.getIntroDescriptor();
		introPart = introDescriptor == null ? null : introDescriptor.createIntro();
		if (introPart != null) {
			workbench.getExtensionTracker().registerObject(
					introDescriptor.getConfigurationElement().getDeclaringExtension(), introPart,
