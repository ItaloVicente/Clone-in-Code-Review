package org.eclipse.ui.dialogs;

import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;

public interface IStyledStringHighlighter {

	public StyledString highlight(String text, String pattern, Styler styler);

}
