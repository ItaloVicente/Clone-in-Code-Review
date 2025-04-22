package org.eclipse.egit.ui;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jgit.lib.Repository;

public interface IGitSelectRepositoryWizardPage extends IWizardPage {

	public abstract Repository getRepository();

}
