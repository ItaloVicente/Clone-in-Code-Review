package org.eclipse.jface.widgets;


import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

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

	public static CompositeFactory composite(int style) {
		return CompositeFactory.newComposite(style);
	}

	public static SpinnerFactory spinner(int style) {
		return SpinnerFactory.newSpinner(style);
	}

	public static TableFactory table(int style) {
		return TableFactory.newTable(style);
	}

	public static TreeFactory tree(int style) {
		return TreeFactory.newTree(style);
	}

	public static TableColumnFactory tableColumn(int style) {
		return TableColumnFactory.newTableColumn(style);
	}

	public static TreeColumnFactory treeColumn(int style) {
		return TreeColumnFactory.newTreeColumn(style);
	}
}
