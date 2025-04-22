
package org.eclipse.jface.tests.labelProviders;

import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class DecoratingLabelProviderTreeTest extends CompositeLabelProviderTest {

	class IntListLabelProvider extends LabelProvider implements IColorProvider,
			IFontProvider {

		public IntListLabelProvider() {
		}

		@Override
		public Color getBackground(Object element) {
			return background;
		}

		@Override
		public Color getForeground(Object element) {
			return foreground;
		}

		@Override
		public Font getFont(Object element) {
			return font;
		}
	}

	public DecoratingLabelProviderTreeTest(String name) {
		super(name);
	}

	@Override
	protected StructuredViewer createViewer(Composite parent) {

		initializeColors(parent);
		StructuredViewer viewer = new TreeViewer(parent);
		viewer.setContentProvider(new TestTreeContentProvider());

		viewer.setLabelProvider(new DecoratingLabelProvider(
				new IntListLabelProvider(), null));
		return viewer;
	}
	
	public void testColorsAndFonts() {
		Tree tree = (Tree) fViewer.getControl();
		TreeItem item = tree.getItem(0);

		assertTrue("Background was not set", item.getBackground(0).equals(
				background));
		assertTrue("Foreground was not set", item.getForeground(0).equals(
				foreground));
		assertTrue("Font was not set", item.getFont(0).equals(font));

	}

}
