
package org.eclipse.ui.tests.navigator.extension;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class TestLabelProviderBlue extends TestStyledLabelProvider {

	public static TestLabelProviderBlue instance;
	
	protected void initSubclass() {
		backgroundColor = Display.getCurrent().getSystemColor(
				SWT.COLOR_BLUE);
		backgroundColorName = "Blue";
		font = new Font(Display.getDefault(), boldFontData);
		image = PlatformUI.getWorkbench().getSharedImages().getImage(
				ISharedImages.IMG_ETOOL_SAVE_EDIT);
		instance = this;
	}

}
