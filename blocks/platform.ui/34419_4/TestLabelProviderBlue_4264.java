
package org.eclipse.ui.tests.navigator.extension;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;

public class TestLabelProviderBlank extends TestStyledLabelProvider {

	public static TestLabelProviderBlank instance;

	@Override
	protected void initSubclass() {
		backgroundColor = Display.getCurrent().getSystemColor(SWT.COLOR_RED);
		backgroundColorName = "Red";
		font = new Font(Display.getDefault(), boldFontData);
		image = PlatformUI.getWorkbench().getSharedImages().getImage(
				ISharedImages.IMG_OBJ_ADD);
		instance = this;
	}

	@Override
	public void init(ICommonContentExtensionSite aSite) {
		super.init(aSite);
		_blank = true;
	}

}
