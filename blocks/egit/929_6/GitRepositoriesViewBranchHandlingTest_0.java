package org.eclipse.egit.ui.view.repositories;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses( { GitRepositoriesViewBranchHandlingTest.class,//
		GitRepositoriesViewRepoHandlingTest.class,//
		GitRepositoriesViewRemoteHandlingTest.class,//
		GitRepositoriesViewTest.class //
})
public class AllRepositoriesViewTests {
}
