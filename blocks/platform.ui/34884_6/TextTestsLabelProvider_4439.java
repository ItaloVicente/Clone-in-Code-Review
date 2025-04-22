package org.eclipse.ui.tests.views.properties.tabbed.text;

import java.util.StringTokenizer;

import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class TextTestsLabelProvider extends LabelProvider {

	public Image getImage(Object obj) {
		return PlatformUI.getWorkbench().getSharedImages().getImage(
				ISharedImages.IMG_OBJ_FILE);
	}

	public String getText(Object obj) {
		if (obj instanceof ITextSelection) {
			ITextSelection textSelection = (ITextSelection) obj;
			if (textSelection.getLength() != 0) {
				StringTokenizer tokenizer = new StringTokenizer(textSelection
						.getText());
				int size = 0;
				while (tokenizer.hasMoreTokens()) {
					size++;
					tokenizer.nextToken();
				}
				if (size == 1) {
					return textSelection.getText();
				}
				return size + " words selected";//$NON-NLS-1$
			}
		}
		return null;
	}
}
