 */
public class GitSynchronizeWizard extends Wizard {

	private GitSynchronizeWizardPage page;

	/**
	 * Instantiates a new wizard for synchronizing resources that are being
	 * managed by EGit.
	 */
	public GitSynchronizeWizard() {
		setWindowTitle(UIText.GitSynchronizeWizard_synchronize);
	}

	@Override
	public void addPages() {
		page = new GitSynchronizeWizardPage();
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		Set<IProject> projects = page.getSelectedProjects();
		GitSynchronizeDataSet gsdSet = new GitSynchronizeDataSet();

		Map<Repository, String> branches = page.getSelectedBranches();
		for (Repository repo : branches.keySet()) {
			gsdSet.add(new GitSynchronizeData(repo, Constants.HEAD, branches.get(repo), projects, false));
		}

		new GitSynchronize(gsdSet);

		return true;
	}

}
