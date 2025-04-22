
package org.eclipse.jface.tests.labelProviders;

import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class ColorAndFontLabelProviderTest extends CompositeLabelProviderTest {

	class ColorAndFontProvider extends LabelProvider implements IColorProvider,
			IFontProvider {

		public ColorAndFontProvider() {
			super();
		}

		@Override
		public Font getFont(Object element) {
			return font;
		}

		@Override
		public Color getBackground(Object element) {
			return background;
		}

		@Override
		public Color getForeground(Object element) {
			return foreground;
		}


	}

	public ColorAndFontLabelProviderTest(String name) {
		super(name);
	}

	@Override
	protected StructuredViewer createViewer(Composite parent) {
		initializeColors(parent);
		final TableViewer v = new TableViewer(parent);
		v.setContentProvider(new LabelTableContentProvider());
		v.setLabelProvider(new ColorAndFontProvider());
		;
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
		
		Font oldFont = font;

		clearColors();
		fViewer.refresh(item.getData());

		Display display = table.getDisplay();
		assertTrue("Background was not cleared", item.getBackground(0).equals(
				display.getSystemColor(SWT.COLOR_LIST_BACKGROUND)));
		assertTrue("Foreground was not cleared", item.getForeground(0).equals(
				display.getSystemColor(SWT.COLOR_LIST_FOREGROUND)));
		assertFalse("Font was not cleared", item.getFont(0).getFontData()[0]
				.equals(oldFont.getFontData()[0]));

	}

	private void clearColors() {
		background = null;
		foreground = null;
		font = null;

	}

}
