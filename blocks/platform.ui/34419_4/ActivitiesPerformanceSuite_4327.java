
package org.eclipse.jface.tests.performance;

import org.eclipse.jface.util.Util;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.tests.performance.BasicPerformanceTest;

public abstract class ViewerTest extends BasicPerformanceTest {

	Shell browserShell;
	
	public static int ITERATIONS = 100;
	public static int MIN_ITERATIONS = 20;

	public ViewerTest(String testName, int tagging) {
		super(testName, tagging);
	}

	public ViewerTest(String testName) {
		super(testName);
	}

	protected void openBrowser() {
		Display display = Display.getCurrent();
		if (display == null) {
			display = new Display();
		}
		browserShell = new Shell(display);
		browserShell.setSize(500, 500);
		browserShell.setLayout(new FillLayout());
		StructuredViewer viewer = createViewer(browserShell);
		viewer.setUseHashlookup(true);
		viewer.setInput(getInitialInput());
		browserShell.open();
	}

	protected Object getInitialInput() {
		return this;
	}

	protected abstract StructuredViewer createViewer(Shell shell);

	public ILabelProvider getLabelProvider() {
		return new LabelProvider() {
			public String getText(Object element) {
				return ((TestElement) element).getText();
			}

		};
	}
	
	protected void doTearDown() throws Exception {
		super.doTearDown();
		if(browserShell!= null){
			browserShell.close();
			browserShell = null;
		}
	}
	
	public int slowGTKIterations(){
		if(Util.isGtk())
			return ITERATIONS / 5;
		return ITERATIONS;
	}
	
	
	public int slowWindowsIterations(){
		if(Util.isWindows())
			return ITERATIONS / 5;
		return ITERATIONS;
	}

}
