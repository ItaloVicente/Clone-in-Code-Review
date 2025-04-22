package org.eclipse.egit.ui.submodule;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ SubmoduleAddTest.class, //
		SubmoduleSyncTest.class, //
		SubmoduleUpdateTest.class, //
})
public class SubmoduleTests {
}
