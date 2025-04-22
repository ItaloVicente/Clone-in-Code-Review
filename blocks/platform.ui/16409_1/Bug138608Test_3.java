
package org.eclipse.jface.tests.viewers;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Shell;

public class ArrayContentProviderTest extends TestCase {

	private Shell shell;

	protected void setUp() throws Exception {
		super.setUp();

		shell = new Shell();
		shell.setLayout(new GridLayout());
		shell.setSize(new Point(500, 200));

		shell.open();
	}

	private List<String> getInput(){
		List<String> input = new ArrayList<String>();
		input.add("Test 1");
		input.add("Test 2");

		return input;
	}

	public void testGenericArrayProvider() throws Exception {
		ListViewer<String, List<String>> listViewer = new ListViewer<String, List<String>>(shell);
		listViewer.setContentProvider(new ArrayContentProvider<String>(String.class));
		listViewer.setInput(getInput());


		String[] items = listViewer.getList().getItems();
		assertEquals(items[0], "Test 1");
		assertEquals(items[1], "Test 2");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void testNonGenericArrayProvider() throws Exception {
		ListViewer listViewer = new ListViewer(shell);
		listViewer.setContentProvider(new ArrayContentProvider());
		listViewer.setInput(getInput());


		String[] items = listViewer.getList().getItems();
		assertEquals(items[0], "Test 1");
		assertEquals(items[1], "Test 2");
	}

	public void testGenericArrayProviderSingelton() throws Exception {
		ArrayContentProvider<String> firstArrayContentProviderOfString = ArrayContentProvider.getInstance(String.class);
		ArrayContentProvider<String> secondArrayContentProviderOfString = ArrayContentProvider.getInstance(String.class);
		ArrayContentProvider<Integer> firstArrayContentProviderOfInteger = ArrayContentProvider.getInstance(Integer.class);
		assertSame(firstArrayContentProviderOfString, secondArrayContentProviderOfString);
		assertNotSame(firstArrayContentProviderOfString, firstArrayContentProviderOfInteger);
	}

	@SuppressWarnings("rawtypes")
	public void testNonGenericArrayProviderSingelton() throws Exception {
		ArrayContentProvider firstArrayContentProvider = ArrayContentProvider.getInstance();
		ArrayContentProvider secondArrayContentProvider = ArrayContentProvider.getInstance();
		assertSame(firstArrayContentProvider,secondArrayContentProvider);
	}

	protected void tearDown() throws Exception {
		shell.close();
		super.tearDown();
	}

}
