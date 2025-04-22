package org.eclipse.egit.ui.test;

import org.eclipse.egit.ui.prefpages.configuration.GlobalConfigurationPageTest;
import org.eclipse.egit.ui.test.history.HistoryViewTest;
import org.eclipse.egit.ui.test.team.actions.AllTeamActionTests;
import org.eclipse.egit.ui.view.repositories.AllRepositoriesViewTests;
import org.eclipse.egit.ui.wizards.share.SharingWizardTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses( { AllRepositoriesViewTests.class, 	//
		GlobalConfigurationPageTest.class,			//
		SharingWizardTest.class,					//
		AllTeamActionTests.class,                   //
		HistoryViewTest.class })
public class AllLocalTests {
}
