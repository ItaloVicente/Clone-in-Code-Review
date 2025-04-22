package org.eclipse.egit.ui.internal.clone;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.egit.core.op.ConnectProviderOperation;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIText;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.NewWizardAction;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

public class GitCreateProjectViaWizardWizard extends Wizard {

	private final Repository myRepository;

	private final String myGitDir;

	private final IProject[] previousProjects;

	private GitSelectWizardPage mySelectionPage;

	private GitCreateGeneralProjectPage myCreateGeneralProjectPage;

	private GitProjectsImportPage myProjectsImportPage;

	private GitShareProjectsPage mySharePage;

	public GitCreateProjectViaWizardWizard(Repository repository, String path) {
		super();
		previousProjects = ResourcesPlugin.getWorkspace().getRoot()
				.getProjects();
		myRepository = repository;
		myGitDir = path;
		setWindowTitle(NLS.bind(
				UIText.GitCreateProjectViaWizardWizard_WizardTitle,
				myRepository.getDirectory().getPath()));


	}

	@Override
	public void addPages() {

		mySelectionPage = new GitSelectWizardPage();
		addPage(mySelectionPage);
		myCreateGeneralProjectPage = new GitCreateGeneralProjectPage(myGitDir);
		addPage(myCreateGeneralProjectPage);
		myProjectsImportPage = new GitProjectsImportPage(false) {

			@Override
			public void setVisible(boolean visible) {
				setGitDir(myRepository.getDirectory());
				setProjectsList(myGitDir);
				super.setVisible(visible);
			}

		};
		addPage(myProjectsImportPage);
		mySharePage = new GitShareProjectsPage();
		addPage(mySharePage);
	}

	@Override
	public IWizardPage getNextPage(IWizardPage page) {

		if (page == mySelectionPage) {

			switch (mySelectionPage.getWizardSelection()) {
			case GitSelectWizardPage.EXISTING_PROJECTS_WIZARD:
				return myProjectsImportPage;
			case GitSelectWizardPage.NEW_WIZARD:
				if (mySelectionPage.getActionSelection() != GitSelectWizardPage.ACTION_DIALOG_SHARE)
					return null;
				else
					return mySharePage;

			case GitSelectWizardPage.GENERAL_WIZARD:
				return myCreateGeneralProjectPage;

			}

			return super.getNextPage(page);

		} else if (page == myCreateGeneralProjectPage
				|| page == myProjectsImportPage) {

			if (mySelectionPage.getActionSelection() != GitSelectWizardPage.ACTION_DIALOG_SHARE)
				return null;
			else
				return mySharePage;
		}
		return super.getNextPage(page);
	}

	@Override
	public boolean canFinish() {

		boolean showSharePage = mySelectionPage.getActionSelection() == GitSelectWizardPage.ACTION_DIALOG_SHARE;
		boolean showShareComplete = !showSharePage
				|| mySharePage.isPageComplete();

		switch (mySelectionPage.getWizardSelection()) {
		case GitSelectWizardPage.EXISTING_PROJECTS_WIZARD:
			return myProjectsImportPage.isPageComplete() && showShareComplete;
		case GitSelectWizardPage.NEW_WIZARD:
			return showShareComplete;
		case GitSelectWizardPage.GENERAL_WIZARD:
			return myCreateGeneralProjectPage.isPageComplete()
					&& showShareComplete;
		}
		return super.canFinish();

	}

	@Override
	public boolean performFinish() {

		try {

			final int actionSelection = mySelectionPage.getActionSelection();

			final IProject[] projectsToShare;
			if (actionSelection != GitSelectWizardPage.ACTION_DIALOG_SHARE) {
				projectsToShare = getAddedProjects();
			} else {
				projectsToShare = mySharePage.getSelectedProjects();
			}

			getContainer().run(true, true, new IRunnableWithProgress() {

				public void run(IProgressMonitor monitor)
						throws InvocationTargetException, InterruptedException {

					if (actionSelection != GitSelectWizardPage.ACTION_DIALOG_SHARE) {
						importProjects();
					}

					if (actionSelection != GitSelectWizardPage.ACTION_NO_SHARE) {

						for (IProject prj : projectsToShare) {
							if (monitor.isCanceled())
								throw new InterruptedException();
							ConnectProviderOperation connectProviderOperation = new ConnectProviderOperation(
									prj, myRepository.getDirectory());
							try {
								connectProviderOperation.run(monitor);
							} catch (CoreException e) {
								throw new InvocationTargetException(e);
							}
						}

					}

				}
			});
		} catch (InvocationTargetException e) {
			Activator
					.handleError(e.getCause().getMessage(), e.getCause(), true);
			return false;
		} catch (InterruptedException e) {
			Activator.handleError(
					UIText.GitCreateProjectViaWizardWizard_AbortedMessage, e,
					true);
			return false;
		}
		return true;

	}

	public void importProjects() {

		Display.getDefault().syncExec(new Runnable() {

			public void run() {

				switch (mySelectionPage.getWizardSelection()) {
				case GitSelectWizardPage.EXISTING_PROJECTS_WIZARD:
					myProjectsImportPage.createProjects();
					break;
				case GitSelectWizardPage.NEW_WIZARD:
					new NewWizardAction(PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow()).run();
					break;
				case GitSelectWizardPage.GENERAL_WIZARD:
					try {
						final String projectName = myCreateGeneralProjectPage
								.getProjectName();
						getContainer().run(true, false,
								new WorkspaceModifyOperation() {

									@Override
									protected void execute(
											IProgressMonitor monitor)
											throws CoreException,
											InvocationTargetException,
											InterruptedException {

										final IProjectDescription desc = ResourcesPlugin
												.getWorkspace()
												.newProjectDescription(
														projectName);
										desc.setLocation(new Path(myGitDir));

										IProject prj = ResourcesPlugin
												.getWorkspace().getRoot()
												.getProject(desc.getName());
										prj.create(desc, monitor);
										prj.open(monitor);

										ResourcesPlugin.getWorkspace()
												.getRoot().refreshLocal(
														IResource.DEPTH_ONE,
														monitor);

									}
								});
					} catch (InvocationTargetException e1) {
						Activator.handleError(e1.getMessage(), e1
								.getTargetException(), true);
					} catch (InterruptedException e1) {
						Activator.handleError(e1.getMessage(), e1, true);
					}
					break;

				}
			}
		});
	}

	public IProject[] getAddedProjects() {

		IProject[] currentProjects = ResourcesPlugin.getWorkspace().getRoot()
				.getProjects();

		List<IProject> newProjects = new ArrayList<IProject>();

		for (IProject current : currentProjects) {
			boolean found = false;
			for (IProject previous : previousProjects) {
				if (previous.equals(current)) {
					found = true;
					break;
				}
			}
			if (!found) {
				newProjects.add(current);
			}
		}

		return newProjects.toArray(new IProject[0]);
	}

}
