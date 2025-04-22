package org.eclipse.egit.ui.internal.repository;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIText;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

public class CreateBranchWizard extends Wizard {
	public CreateBranchWizard(Repository repository, Ref baseBranch) {
		myPage = new CreateBranchPage(repository, baseBranch);
		setWindowTitle(UIText.CreateBranchWizard_NewBranchTitle);
	}

	public CreateBranchWizard(Repository repository, RevCommit baseCommit) {
		myPage = new CreateBranchPage(repository, baseCommit);
		setWindowTitle(UIText.CreateBranchWizard_NewBranchTitle);
	}

	private CreateBranchPage myPage;

	@Override
	public void addPages() {
		addPage(myPage);
	}

	@Override
	public boolean performFinish() {
		try {
			getContainer().run(false, true, new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor)
						throws InvocationTargetException, InterruptedException {
					CreateBranchPage cp = (CreateBranchPage) getPages()[0];
					try {
						cp.createBranch(monitor);
					} catch (CoreException ce) {
						throw new InvocationTargetException(ce);
					} catch (IOException ioe) {
						throw new InvocationTargetException(ioe);
					}
				}
			});
		} catch (InvocationTargetException ite) {
			Activator.handleError(UIText.CreateBranchWizard_CreationFailed, ite
					.getCause(), true);
			return false;
		} catch (InterruptedException ie) {
		}
		return true;
	}
}
