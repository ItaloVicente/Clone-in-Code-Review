	private static void removeSmartImportWizardToForceGitImportWizardUsage()
			throws Exception {
		final String smartImportWizardId = "org.eclipse.e4.ui.importer.wizard";
		AbstractExtensionWizardRegistry wizardRegistry = (AbstractExtensionWizardRegistry) PlatformUI
				.getWorkbench().getImportWizardRegistry();
		IWizardCategory[] categories = PlatformUI.getWorkbench()
				.getImportWizardRegistry().getRootCategory().getCategories();
		for (IWizardDescriptor wizard : getAllWizards(categories)) {
			if (wizard.getId().equals(smartImportWizardId)) {
				WorkbenchWizardElement wizardElement = (WorkbenchWizardElement) wizard;
				wizardRegistry.removeExtension(
						wizardElement.getConfigurationElement()
								.getDeclaringExtension(),
						new Object[] { wizardElement });
				return;
			}
		}
	}

	private static IWizardDescriptor[] getAllWizards(
			IWizardCategory[] categories) {
		List<IWizardDescriptor> results = new ArrayList<IWizardDescriptor>();
		for (IWizardCategory wizardCategory : categories) {
			results.addAll(Arrays.asList(wizardCategory.getWizards()));
			results.addAll(Arrays
					.asList(getAllWizards(wizardCategory.getCategories())));
		}
		return results.toArray(new IWizardDescriptor[0]);
	}

