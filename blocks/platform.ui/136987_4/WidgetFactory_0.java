package org.eclipse.jface.widgets;


import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public final class WidgetFactory {
	private WidgetFactory() {
	}

	public static ButtonFactory button(int style) {
		return ButtonFactory.newButton(style);
	}

	public static TextFactory text(int style) {
		return TextFactory.newText(style);
	}

	public static LabelFactory label(int style) {
		return LabelFactory.newLabel(style);
	}

}
