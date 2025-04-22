
package org.eclipse.core.tests.databinding.observable.list;

import org.eclipse.core.databinding.observable.list.ListDiffVisitor;

import junit.framework.TestCase;

public class ListDiffVisitorTest extends TestCase {
	ListDiffVisitorStub visitor;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		visitor = new ListDiffVisitorStub();
	}

	public void testHandleMove_DelegatesByDefault() {
		visitor.handleMove(0, 1, "element");
		assertEquals(
				"Default ListDiffVisitor.handleMove must delegate to handleRemove and handleAdd",
				"remove(0,element), add(1,element)", visitor.log);
	}

	public void testHandleReplace_DelegatesByDefault() {
		visitor.handleReplace(2, "oldElement", "newElement");
		assertEquals(
				"Default ListDiffVisitor.handleReplace must delegate to handleRemove and handleAdd",
				"remove(2,oldElement), add(2,newElement)", visitor.log);
	}

	static class ListDiffVisitorStub extends ListDiffVisitor {
		String log = "";

		private void log(String message) {
			if (log.length() > 0)
				log += ", ";
			log += message;
		}

		@Override
		public void handleAdd(int index, Object element) {
			log("add(" + index + "," + element + ")");
		}

		@Override
		public void handleRemove(int index, Object element) {
			log("remove(" + index + "," + element + ")");
		}
	}
}
