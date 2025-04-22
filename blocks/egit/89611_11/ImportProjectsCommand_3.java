			RepositoryTreeNode node, String path, List<String> pPaths) {
		if (pPaths.size() == 1) {
			path = pPaths.get(0);
		}
		GitCreateProjectViaWizardWizard wizard = new GitCreateProjectViaWizardWizard(
				node.getRepository(), path);
		if (pPaths.size() > 1) {
			wizard.setFilter(pPaths);
		}
		WizardDialog dlg = new WizardDialog(getShell(event), wizard) {
