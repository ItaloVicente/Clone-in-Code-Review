
package org.eclipse.e4.ui.tests.application;

import org.eclipse.e4.ui.model.application.ui.basic.MPart;

public class UIPhotoDemoTest extends UIStartupTest {

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

}
