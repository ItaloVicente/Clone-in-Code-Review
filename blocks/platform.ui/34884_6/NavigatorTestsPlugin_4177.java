package org.eclipse.ui.tests.navigator;

import org.eclipse.ui.tests.navigator.cdt.CdtTest;
import org.eclipse.ui.tests.navigator.jst.JstPipelineTest;

import junit.framework.Test;
import junit.framework.TestSuite;

public final class NavigatorTestSuite extends TestSuite {

	public static final Test suite() {
		return new NavigatorTestSuite();
	}

	public NavigatorTestSuite() {
		addTest(new TestSuite(InitialActivationTest.class));
		addTest(new TestSuite(ActionProviderTest.class));
		addTest(new TestSuite(ExtensionsTest.class));
		addTest(new TestSuite(FilterTest.class));
		addTest(WorkingSetTest.suite());
		addTest(new TestSuite(ActivityTest.class));
		addTest(new TestSuite(OpenTest.class));
		addTest(new TestSuite(INavigatorContentServiceTests.class));
		addTest(new TestSuite(ProgrammaticOpenTest.class));
		addTest(new TestSuite(PipelineTest.class));
		addTest(new TestSuite(PipelineChainTest.class));
		addTest(new TestSuite(JstPipelineTest.class));
		addTest(new TestSuite(LabelProviderTest.class));
		addTest(new TestSuite(SorterTest.class));
		addTest(new TestSuite(ViewerTest.class));
		addTest(new TestSuite(CdtTest.class));
		addTest(new TestSuite(M12Tests.class));
		addTest(new TestSuite(FirstClassM1Tests.class));
		addTest(new TestSuite(LinkHelperTest.class));
	}

}
