package org.eclipse.e4.ui.tests.application;

import junit.framework.TestCase;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.contributions.IContributionFactory;
import org.eclipse.e4.ui.internal.workbench.swt.E4Application;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.impl.ApplicationFactoryImpl;
import org.eclipse.e4.ui.workbench.IPresentationEngine;

public class UITest extends TestCase {

	final static private String engineURI = "bundleclass://org.eclipse.e4.ui.tests/org.eclipse.e4.ui.tests.application.HeadlessContextPresentationEngine"; //$NON-NLS-1$

	protected IEclipseContext applicationContext;
	protected MApplication application;

	private IPresentationEngine engine;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		application = ApplicationFactoryImpl.eINSTANCE.createApplication();
		applicationContext = E4Application.createDefaultContext();
		application.setContext(applicationContext);
		applicationContext.set(MApplication.class, application);

		E4Application.initializeServices(application);
	}

	@Override
	protected void tearDown() throws Exception {
		applicationContext.dispose(); // used by the tests to dispose GUI?
		super.tearDown();
	}

	protected IPresentationEngine getEngine() {
		if (engine == null) {
			IContributionFactory contributionFactory = (IContributionFactory) applicationContext
					.get(IContributionFactory.class.getName());
			Object newEngine = contributionFactory.create(engineURI,
					applicationContext);
			assertTrue(newEngine instanceof IPresentationEngine);
			applicationContext.set(IPresentationEngine.class.getName(),
					newEngine);

			engine = (IPresentationEngine) newEngine;
		}

		return engine;
	}

}
