
package org.eclipse.jface.tests.viewers;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Composite;

public class Bug327004TreeViewerTest extends ViewerTestCase {

	private TreeViewer<TreeNode<String>,TreeNode<String>[]> treeViewer;
	private TreeNode<String>[] rootElements;


	public Bug327004TreeViewerTest(String name) {
		super(name);
	}

	protected StructuredViewer createViewer(Composite parent) {
		treeViewer = new TreeViewer<TreeNode<String>,TreeNode<String>[]>(parent);
		treeViewer.setContentProvider(new ITreeContentProvider<TreeNode<String>, TreeNode<String>[]>() {

			public void dispose() {

			}

			public void inputChanged(Viewer<TreeNode<String>[]> viewer,
					TreeNode<String>[] oldInput, TreeNode<String>[] newInput) {

			}

			public TreeNode<String>[] getElements(
					TreeNode<String>[] inputElement) {
				return inputElement;
			}

			public TreeNode<String>[] getChildren(TreeNode<String> parentElement) {
				return parentElement.getChildren();
			}

			public TreeNode<String> getParent(TreeNode<String> element) {
				return element.getParent();
			}

			public boolean hasChildren(TreeNode<String> element) {
				return element.hasChildren();
			}
		});
		return treeViewer;
	}

	@Override
	protected void setUpModel() {
		rootElements= new TreeNode[5];
		rootElements[0]= new TreeNode<String>("0");
		rootElements[1]= new TreeNode<String>("1");
		rootElements[2]= new TreeNode<String>("1");
		rootElements[3]= new TreeNode<String>("1");
		rootElements[4]= new TreeNode<String>("1");

	}

	@Override
	protected void setInput() {
		treeViewer.setInput(rootElements);
	}

	public void test327004() {


		ViewerFilter<TreeNode<String>,TreeNode<String>[]> filter= new ViewerFilter<TreeNode<String>,TreeNode<String>[]>() {
			public boolean select(Viewer<TreeNode<String>[]> viewer, Object parentElement, TreeNode<String> element) {
				if (element == rootElements[0] || element == rootElements[1] || element == rootElements[2] || element == rootElements[4])
					return false;
				return true;
			}
		};
		treeViewer.addFilter(filter);
		int i= treeViewer.getTree().getItemCount();

		assertEquals(4, i); // 4 because the filter doesn't work due to equal nodes
	}

}
