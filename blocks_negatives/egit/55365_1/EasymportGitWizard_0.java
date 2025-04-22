public class EasymportGitWizard extends AbstractGitCloneWizard implements IImportWizard {

	private SelectImportRootWizardPage selectRootPage = new SelectImportRootWizardPage(this, null, null) {
		@Override
		public void setVisible(boolean visible) {
			if (visible) {
				if (existingRepo != null) {
					this.setInitialSelectedDirectory(existingRepo.getWorkTree());
				} else if (needToCloneRepository()) {
					this.setInitialSelectedDirectory(doClone());
				}
			}
			super.setVisible(visible);
		}
	};
