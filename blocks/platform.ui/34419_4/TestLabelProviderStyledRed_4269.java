
package org.eclipse.ui.tests.navigator.extension;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class TestLabelProviderStyledGreen extends TestStyledLabelProvider {
	public static TestLabelProviderStyledGreen instance;

	@Override
	protected void initSubclass() {
		backgroundColor = Display.getCurrent().getSystemColor(
				SWT.COLOR_GREEN);
		backgroundColorName = "Green";
		font = new Font(Display.getDefault(), boldFontData);
		image = PlatformUI.getWorkbench().getSharedImages().getImage(
				ISharedImages.IMG_ELCL_COLLAPSEALL);
		instance = this;
	}

}
