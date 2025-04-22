package org.eclipse.jface.tests.viewers;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class ListViewerRefreshTest extends TestCase {
	private static final int DELAY = 0;

	private Shell shell = null;

	private Label label = null;

	private ListViewer viewer = null;

	private ArrayList input = null;

	@Override
	protected void setUp() throws Exception {
		shell = new Shell();
		shell.setSize(400, 200);
		shell.setLayout(new FillLayout());
		label = new Label(shell, SWT.WRAP);
		viewer = new ListViewer(shell);
		input = new ArrayList();

		for (int i = 0; i < 50; i++) {
			input.add("item " + i); //$NON-NLS-1$
		}

		viewer.setContentProvider(new ContentProvider());
		viewer.setInput(input);
		shell.layout();
		shell.open();
	}
	
	@Override
	protected void tearDown() throws Exception {
		shell.dispose();
		shell = null;
	}

	public void testNoSelectionRefresh() throws Exception {
		shell.setText("Lost Scrolled Position Test"); //$NON-NLS-1$
		readAndDispatch();

		run("Scrolled to position 30.", new Runnable() { //$NON-NLS-1$
					@Override
					public void run() {
						viewer.reveal(input.get(30));
					}
				});

		run("Refreshed viewer without a selection.", new Runnable() { //$NON-NLS-1$
					@Override
					public void run() {
						viewer.refresh();
					}
				});

		assertTrue(viewer.getList().getTopIndex() != 0);
	}

	public void testSelectionRefresh() throws Exception {
		shell.setText("Preserved Scrolled Position Test"); //$NON-NLS-1$
		readAndDispatch();

		run("Setting selection to index 30.", new Runnable() { //$NON-NLS-1$
					@Override
					public void run() {
						viewer.setSelection(new StructuredSelection(input
								.get(30)));
					}
				});
		
		viewer.getList().setTopIndex(0);
		
		run("Refreshed viewer with selection.", new Runnable() { //$NON-NLS-1$
					@Override
					public void run() {
						viewer.refresh();
					}
				});
		
		assertTrue(viewer.getList().getTopIndex() == 0);
		
		viewer.getList().showSelection();
		
		assertTrue(viewer.getList().getTopIndex() != 0);
	}
	
	private void run(String description, Runnable runnable) {
		runnable.run();
		label.setText(description);

		readAndDispatch();
	}

	private void readAndDispatch() {
		Display display = Display.getCurrent();
		while(display.readAndDispatch());

		try {
			Thread.sleep(DELAY);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private class ContentProvider implements IStructuredContentProvider {

		@Override
		public Object[] getElements(Object inputElement) {
			return ((List) inputElement).toArray();
		}

		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}
}
