package org.eclipse.egit.ui.internal.synchronize.model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ GitModelBlobTest.class,
		GitModelCacheFileTest.class,
		GitModelCacheTest.class,
		GitModelCacheTreeTest.class,
		GitModelCommitTest.class,
		GitModelRepositoryTest.class,
		GitModelTreeTest.class,
		GitModelWorkingTreeTest.class,
		GitModelWorkingFileTest.class })
public class AllGitModelTests {
}
