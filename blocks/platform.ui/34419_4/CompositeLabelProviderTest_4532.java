
package org.eclipse.jface.tests.labelProviders;

import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.IViewerLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class CompositeLabelProviderTableTest extends CompositeLabelProviderTest {

	class MyLabelProvider extends LabelProvider implements IColorProvider,
			IViewerLabelProvider, IFontProvider {
		@Override
		public Color getForeground(Object element) {
			return foreground;
		}

		@Override
		public Color getBackground(Object element) {
			return background;
		}

		@Override
		public void updateLabel(ViewerLabel label, Object element) {
			label.setText(getText(element));
		}

		@Override
		public Font getFont(Object element) {
			return font;
		}
	}

	public CompositeLabelProviderTableTest(String name) {
		super(name);
	}

	@Override
	protected StructuredViewer createViewer(Composite parent) {

		initializeColors(parent);
		final TableViewer v = new TableViewer(parent);
		v.setContentProvider(new LabelTableContentProvider());
		v.setLabelProvider(new MyLabelProvider());
		v.getTable().setLinesVisible(true);
		return v;
	}

	public void testColorsAndFonts() {
		Table table = (Table) fViewer.getControl();
		TableItem item = table.getItem(0);

		assertTrue("Background was not set", item.getBackground(0).equals(
				background));
		assertTrue("Foreground was not set", item.getForeground(0).equals(
				foreground));
		assertTrue("Font was not set", item.getFont(0).equals(font));

	}

}
