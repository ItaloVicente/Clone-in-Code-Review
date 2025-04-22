package org.eclipse.egit.ui.test.team.actions;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses( { BranchAndResetActionTest.class, //
		TagActionTest.class, //
		CommitActionTest.class, //
		PushActionTest.class, //
		FetchAndMergeActionTest.class, //
		DisconnectConnectTest.class //
})
public class AllTeamActionTests {
}
