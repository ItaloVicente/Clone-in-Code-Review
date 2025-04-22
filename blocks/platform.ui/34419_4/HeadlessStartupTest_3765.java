
package org.eclipse.e4.ui.tests.application;

import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.IPresentationEngine;

public class HeadlessPhotoDemoTest extends HeadlessApplicationTest {

	@Override
	protected String getURI() {
		return "org.eclipse.e4.ui.tests/xmi/photo.e4xmi";
	}

	@Override
	protected MPart getFirstPart() {
		return (MPart) findElement("ThumbnailsView");
	}

	@Override
	protected MPart getSecondPart() {
		return (MPart) findElement("ExifView");
	}

	protected IPresentationEngine createPresentationEngine(
			String renderingEngineURI) throws Exception {
		HeadlessContextPresentationEngine engine = (HeadlessContextPresentationEngine) super
				.createPresentationEngine(renderingEngineURI);
		engine.setCreateContributions(false);
		return engine;
	}

}
