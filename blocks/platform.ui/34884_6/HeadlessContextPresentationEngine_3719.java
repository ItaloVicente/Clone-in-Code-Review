
package org.eclipse.e4.ui.tests.application;

import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.IPresentationEngine;

public class HeadlessContactsDemoTest extends HeadlessApplicationTest {

	@Override
	protected String getURI() {
		return "org.eclipse.e4.ui.tests/xmi/contacts.e4xmi";
	}

	@Override
	protected MPart getFirstPart() {
		return (MPart) findElement("DetailsView");
	}

	@Override
	protected MPart getSecondPart() {
		return (MPart) findElement("ContactsView");
	}

	protected IPresentationEngine createPresentationEngine(
			String renderingEngineURI) throws Exception {
		HeadlessContextPresentationEngine engine = (HeadlessContextPresentationEngine) super
				.createPresentationEngine(renderingEngineURI);
		engine.setCreateContributions(false);
		return engine;
	}

}
