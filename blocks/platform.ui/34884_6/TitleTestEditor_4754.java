
package org.eclipse.ui.tests.api.workbenchpart;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.menus.WorkbenchWindowControlContribution;

public class TextWidget extends WorkbenchWindowControlContribution {
	public TextWidget() {
		
	}
	
	protected TextWidget(String id) {
		super(id);
	}

	@Override
	protected Control createControl(Composite parent) {
		Composite textHolder = new Composite(parent, SWT.NONE);
		textHolder.setLayout(new Layout() {
			@Override
			protected Point computeSize(Composite composite, int wHint,
					int hHint, boolean flushCache) {
				Text tw = (Text) composite.getChildren()[0];
				Point twSize = tw.computeSize(wHint, hHint, flushCache);
				
				if (twSize.x < 200)
					twSize.x = 200;
				
				return twSize;
			}

			@Override
			protected void layout(Composite composite, boolean flushCache) {
				Text tw = (Text) composite.getChildren()[0];
				Point twSize = tw.computeSize(SWT.DEFAULT, SWT.DEFAULT, flushCache);
				Rectangle bb = composite.getBounds();
				int yOffset = ((bb.height-twSize.y) / 2) + 1;
				if (yOffset < 0) yOffset = 0;
				
				tw.setBounds(0, yOffset, bb.width, twSize.y);
			}
		});
		
		Text tw = new Text(textHolder, SWT.BORDER);
		tw.setText("Test Text Eric was here...XXXXXX");
		
		textHolder.setSize(181, 22);
		return textHolder;
	}
}
