
package org.eclipse.e4.ui.tests.application;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.internal.workbench.UIEventPublisher;
import org.eclipse.e4.ui.model.application.MApplicationElement;
import org.eclipse.emf.common.notify.Notifier;

public abstract class HeadlessApplicationElementTest extends
		HeadlessStartupTest {

	protected MApplicationElement applicationElement;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		applicationElement = createApplicationElement(applicationContext);

		final UIEventPublisher ep = new UIEventPublisher(applicationContext);
		((Notifier) applicationElement).eAdapters().add(ep);
		applicationContext.set(UIEventPublisher.class, ep);
	}

	protected abstract MApplicationElement createApplicationElement(
			IEclipseContext appContext) throws Exception;
}
