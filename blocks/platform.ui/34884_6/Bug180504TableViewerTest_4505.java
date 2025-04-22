
package org.eclipse.jface.tests.viewers;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

public class Bug138608Test extends ViewerTestCase {

	private TreeContentProvider contentProvider;

	public Bug138608Test(String name) {
		super(name);
	}

	@Override
	protected StructuredViewer createViewer(Composite parent) {
		final TreeViewer viewer = new TreeViewer(parent);
		viewer.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));
		contentProvider = new TreeContentProvider();
		LabelProvider labelProvider = new LabelProvider();
		viewer.setContentProvider(contentProvider);
		viewer.setLabelProvider(labelProvider);
		return viewer;
	}

	@Override
	protected void setUpModel() {
	}

	@Override
	protected void setInput() {
		getTreeViewer().setInput(contentProvider.root);
		getTreeViewer().expandAll();
	}

	private TreeViewer getTreeViewer() {
		return (TreeViewer) fViewer;
	}

	public void testBug138608() {
		processEvents();

		contentProvider.root.getChildren()[1].setChildren(contentProvider.root
				.getChildren()[0].getChildren());

		getTreeViewer().add(contentProvider.root.getChildren()[1],
				contentProvider.root.getChildren()[1].getChildren()[1]);

		assertEquals("expected two children of node b", 2, getTreeViewer()
				.getTree().getItem(1).getItemCount());

		getTreeViewer().add(contentProvider.root.getChildren()[1],
				contentProvider.root.getChildren()[1].getChildren()[1]);

		assertEquals("expected two children of node b", 2, getTreeViewer()
				.getTree().getItem(1).getItemCount());

	}

	@Override
	public void tearDown() {
		contentProvider = null;
		super.tearDown();
	}

	private static class TreeContentProvider implements ITreeContentProvider {

		public TreeNode root = new TreeNode("root");

		public TreeContentProvider() {
			TreeNode d = new TreeNode("d");
			TreeNode c = new TreeNode("c");
			TreeNode b = new TreeNode("b");
			TreeNode a = new TreeNode("a");
			root.setChildren(new TreeNode[] { a, b });
			a.setChildren(new TreeNode[] { c, d });
			b.setChildren(new TreeNode[] { c });
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			return ((TreeNode) parentElement).getChildren();
		}

		@Override
		public Object getParent(Object element) {
			return ((TreeNode) element).getParent();
		}

		@Override
		public boolean hasChildren(Object element) {
			return ((TreeNode) element).hasChildren();
		}

		@Override
		public Object[] getElements(Object inputElement) {
			return getChildren(inputElement);
		}

		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}

}
