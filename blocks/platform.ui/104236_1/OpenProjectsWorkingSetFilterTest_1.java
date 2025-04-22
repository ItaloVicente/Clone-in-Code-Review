package org.eclipse.ui.tests.navigator;

import org.eclipse.ui.tests.navigator.cdt.CdtTest;
import org.eclipse.ui.tests.navigator.jst.JstPipelineTest;
import org.eclipse.ui.tests.navigator.resources.NestedResourcesTests;
import org.eclipse.ui.tests.navigator.resources.OpenProjectsWorkingSetFilterTest;
import org.eclipse.ui.tests.navigator.resources.PathComparatorTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ InitialActivationTest.class, ActionProviderTest.class, ExtensionsTest.class, FilterTest.class,
		WorkingSetTest.class, ActivityTest.class, OpenTest.class, INavigatorContentServiceTests.class,
		ProgrammaticOpenTest.class, PipelineTest.class, PipelineChainTest.class, JstPipelineTest.class,
		LabelProviderTest.class, SorterTest.class, ViewerTest.class, CdtTest.class, M12Tests.class,
		FirstClassM1Tests.class, LinkHelperTest.class, ResourceTransferTest.class, EvaluationCacheTest.class,
		NestedResourcesTests.class, PathComparatorTest.class, OpenProjectsWorkingSetFilterTest.class
})
public final class NavigatorTestSuite {
}
