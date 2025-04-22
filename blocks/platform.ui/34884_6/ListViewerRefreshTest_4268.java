
package org.eclipse.jface.tests.performance;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.tests.performance.BasicPerformanceTest;
import org.eclipse.ui.tests.performance.TestRunnable;

public class ListPopulationTest extends BasicPerformanceTest {

	List list;

	public ListPopulationTest(String testName, int tagging) {
		super(testName, tagging);		
	}

	public ListPopulationTest(String testName) {
		super(testName);
	}

	protected void openBrowser() {
		Display fDisplay = Display.getCurrent();
		if (fDisplay == null) {
			fDisplay = new Display();
		}
		Shell shell = new Shell(fDisplay);
		shell.setSize(500, 500);
		shell.setLayout(new FillLayout());
		list = new List(shell,SWT.NONE);
		shell.open();
	}
	
	public void testSmallAdd() throws Throwable {
		addBench(100);
	}
	
	public void testSmallSetItems() throws Throwable {
		setItemsBench(100);
	}
	
	public void testMediumAdd() throws Throwable {
		addBench(5000);
	}
	
	public void testMediumSetItems() throws Throwable {
		setItemsBench(5000);
	}
	
	public void testLargeAdd() throws Throwable {
		addBench(50000);
	}
	
	public void testLargeSetItems() throws Throwable {
		setItemsBench(50000);
	}
	
	public void addBench(int count) throws Throwable {
		openBrowser();		
		final String [] items = getItems(count);
        
        exercise(new TestRunnable() {
            public void run() {
    			list.removeAll();
    			startMeasuring();
    			for (int j = 0; j < items.length; j++) {
    				list.add(items[j]);
    			}
    			processEvents();
    			stopMeasuring();
    		}
        });
		
		commitMeasurements();
		assertPerformance();
	}
	
	public void setItemsBench(int count) throws Throwable {
		openBrowser();		
		final String [] items = getItems(count);
        exercise(new TestRunnable() {
            public void run() {
    			list.removeAll();
    			startMeasuring();
    			list.setItems(items);
    			processEvents();
    			stopMeasuring();
            }
        });
		
		commitMeasurements();
		assertPerformance();
	}

	private String[] getItems(int count) {
		String[] items = new String[count];
		for (int j = 0; j < items.length; j++) {
			items[j] = "Element " + String.valueOf(j);
			
		}
		return items;
	}
	

}
