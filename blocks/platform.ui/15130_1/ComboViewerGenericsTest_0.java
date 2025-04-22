package org.eclipse.jface.tests.viewers;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Composite;

public class CComboViewerGenericsTest extends StructuredViewerGenericsTest {
	public CComboViewerGenericsTest(String name) {
		super(name);
	}

	protected StructuredViewer<TestElement, TestElement> createViewer(
			Composite parent) {
		CCombo cCombo = new CCombo(parent, SWT.READ_ONLY | SWT.BORDER);
		ComboViewer<TestElement, TestElement> viewer = new ComboViewer<TestElement, TestElement>(cCombo);
		viewer.setContentProvider(new TestModelContentProviderGenerics());
		return viewer;
	}

	protected int getItemCount() {
		TestElement first = fRootElement.getFirstChild();
		CCombo list = (CCombo) fViewer.testFindItem(first);
		return list.getItemCount();
	}

	protected String getItemText(int at) {
		CCombo list = (CCombo) fViewer.getControl();
		return list.getItem(at);
	}

	public static void main(String args[]) {
		junit.textui.TestRunner.run(CComboViewerGenericsTest.class);
	}

	public void testInsertChild() {

	}
}
