package org.eclipse.egit.ui.test;

import org.eclipse.egit.ui.view.repositories.GitRepositoriesViewTest;
import org.eclipse.egit.ui.wizards.clone.GitCloneWizardTest;
import org.eclipse.egit.ui.wizards.share.SharingWizardTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses( { GitCloneWizardTest.class, SharingWizardTest.class,
		GitRepositoriesViewTest.class })
public class AllTests {
}
