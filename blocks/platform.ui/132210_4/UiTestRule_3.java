package org.eclipse.e4.ui.tests.rules;

import static org.junit.Assert.assertTrue;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.contributions.IContributionFactory;
import org.eclipse.e4.ui.internal.workbench.swt.E4Application;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.impl.ApplicationFactoryImpl;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class UiTestRule implements TestRule {
	private static final String ENGINE_URI = "bundleclass://org.eclipse.e4.ui.tests/org.eclipse.e4.ui.tests.application.HeadlessContextPresentationEngine"; //$NON-NLS-1$

	public IEclipseContext applicationContext;
	public MApplication application;

	private IPresentationEngine engine;

	public EModelService modelService;

	public IEclipseContext getApplicationContext() {
		return applicationContext;
	}

	@Override
    public Statement apply(Statement base, Description description) {
        return new MyStatement(base);
    }

    public class MyStatement extends Statement {
        private final Statement base;

        public MyStatement(Statement base) {
            this.base = base;
        }

        @Override
        public void evaluate() throws Throwable {
			setUp();
            try {
                base.evaluate();
            } finally {
				tearDown();
            }
        }
    }


	public void setUp() {
		application = ApplicationFactoryImpl.eINSTANCE.createApplication();
		applicationContext = E4Application.createDefaultContext();
		application.setContext(applicationContext);
		applicationContext.set(MApplication.class, application);
		modelService = applicationContext.get(EModelService.class);
		E4Application.initializeServices(application);
	}

	public void tearDown() {
		applicationContext.dispose(); // used by the tests to dispose GUI?
	}

	public IPresentationEngine getEngine() {
		if (engine == null) {
			IContributionFactory contributionFactory = applicationContext.get(IContributionFactory.class);
			Object newEngine = contributionFactory.create(ENGINE_URI, applicationContext);
			assertTrue(newEngine instanceof IPresentationEngine);
			applicationContext.set(IPresentationEngine.class.getName(), newEngine);

			engine = (IPresentationEngine) newEngine;
		}

		return engine;
	}
}
