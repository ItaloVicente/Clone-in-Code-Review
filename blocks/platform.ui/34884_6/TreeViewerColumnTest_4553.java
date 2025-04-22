
package org.eclipse.jface.tests.viewers;

import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;

import junit.framework.TestCase;

public class TreeSelectionTest extends TestCase {
	
	public TreeSelectionTest(String name) {
		super(name);
	}
	
	public void testBug1384558() {
		Object one = new Object();
		Object two = new Object();
		Object three = new Object();
		TreePath[] treePaths1 = new TreePath[3];
		treePaths1[0] = new TreePath(new Object[]{one, two});
		treePaths1[1] = new TreePath(new Object[]{one, three});
		treePaths1[2] = new TreePath(new Object[]{two, two});
		TreeSelection treeSelection1 = new TreeSelection(treePaths1);
		TreePath[] treePaths2 = new TreePath[2];
		treePaths2[0] = new TreePath(new Object[]{one, two});
		treePaths2[1] = new TreePath(new Object[]{one, three});
		TreeSelection treeSelection2 = new TreeSelection(treePaths2);
		assertFalse(treeSelection1.equals(treeSelection2));
	}

}
