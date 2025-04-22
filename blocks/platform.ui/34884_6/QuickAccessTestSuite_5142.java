
package org.eclipse.ui.tests.quickaccess;

import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.model.application.ui.menu.MToolControl;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.internal.WorkbenchWindow;
import org.eclipse.ui.internal.quickaccess.QuickAccessMessages;
import org.eclipse.ui.internal.quickaccess.SearchField;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class QuickAccessDialogTest extends UITestCase {

	private SearchField searchField;

	public QuickAccessDialogTest(String testName) {
		super(testName);
	}

	@Override
	protected void doSetUp() throws Exception {
		WorkbenchWindow workbenchWindow = (WorkbenchWindow) getWorkbench()
				.getActiveWorkbenchWindow();
		MWindow window = workbenchWindow.getModel();
		EModelService modelService = window.getContext().get(
				EModelService.class);
		MToolControl control = (MToolControl) modelService.find(
				"SearchField", window); //$NON-NLS-1$
		searchField = (SearchField) control.getObject();
		assertNotNull("Search Field must exist", searchField);
	}

	@Override
	protected void doTearDown() throws Exception {
		Text text = searchField.getQuickAccessSearchText();
		if (text != null){
			text.setText("");
		}
		Shell shell = searchField.getQuickAccessShell();
		if (shell != null){
			shell.setVisible(false);
		}
	}

	public void testOpenByCommand() throws Exception {
		IHandlerService handlerService = getWorkbench().getActiveWorkbenchWindow()
				.getService(IHandlerService.class);
		Shell shell = searchField.getQuickAccessShell();
		assertFalse("Quick access dialog should not be visible yet", shell.isVisible());
		handlerService
		.executeCommand("org.eclipse.ui.window.quickAccess", null); //$NON-NLS-1$
		assertTrue("Quick access dialog should be visible now", shell.isVisible());
	}

	public void testOpenByText(){
		Shell shell = searchField.getQuickAccessShell();
		assertFalse("Quick access dialog should not be visible yet", shell.isVisible());
		Text text = searchField.getQuickAccessSearchText();
		text.setText("Test");
		assertTrue("Quick access dialog should be visible now", shell.isVisible());
	}

	public void testTextFilter(){
		final Table table = searchField.getQuickAccessTable();
		Text text = searchField.getQuickAccessSearchText();
		assertTrue("Quick access table should say to start typing", table.getItemCount() == 1);
		assertSame("Quick access table should say to start typing", QuickAccessMessages.QuickAccess_StartTypingToFindMatches, table.getItem(0).getText(1));

		text.setText("T");
		processEventsUntil(new Condition() {
			@Override
			public boolean compute() {
				return table.getItemCount() > 1;
			};
		}, 200);
		int oldCount = table.getItemCount();
		assertTrue("Not enough quick access items for simple filter", oldCount > 3);
		assertTrue("Too many quick access items for size of table", oldCount < 30);
		final String oldFirstItemText = table.getItem(0).getText(1);

		text.setText("E");
		processEventsUntil(new Condition() {
			@Override
			public boolean compute() {
				return table.getItemCount() > 1 && !table.getItem(0).getText(1).equals(oldFirstItemText);
			};
		}, 200);
		String newFirstItemText = table.getItem(0).getText(1);
		assertNotSame("The quick access items should have changed", newFirstItemText, oldFirstItemText);
		int newCount = table.getItemCount();
		assertTrue("Not enough quick access items for simple filter", newCount > 3);
		assertTrue("Too many quick access items for size of table", newCount < 30);

		text.setText("QWERTYUIOPTEST");
		processEventsUntil(new Condition() {
			@Override
			public boolean compute() {
				return table.getItemCount() == 1;
			};
		}, 200);
		assertTrue("Quick access table should say no results found", table.getItemCount() == 1);
		assertSame("Quick access table should say no results found", QuickAccessMessages.QuickAccessContents_NoMatchingResults, table.getItem(0).getText());

		text.setText("");
		processEventsUntil(new Condition() {
			@Override
			public boolean compute() {
				return table.getItemCount() == 1;
			};
		}, 200);
		assertTrue("Quick access table should say to start typing", table.getItemCount() == 1);
		assertSame("Quick access table should say to start typing", QuickAccessMessages.QuickAccess_StartTypingToFindMatches, table.getItem(0).getText(1));
	}

	public void testShowAll() throws Exception {
		IHandlerService handlerService = getWorkbench().getActiveWorkbenchWindow()
				.getService(IHandlerService.class);
		Shell shell = searchField.getQuickAccessShell();
		assertFalse("Quick access dialog should not be visible yet", shell.isVisible());
		handlerService
		.executeCommand("org.eclipse.ui.window.quickAccess", null); //$NON-NLS-1$
		assertTrue("Quick access dialog should be visible now", shell.isVisible());
		final Table table = searchField.getQuickAccessTable();
		Text text = searchField.getQuickAccessSearchText();
		assertTrue("Quick access table should say to start typing", table.getItemCount() == 1);
		assertSame("Quick access table should say to start typing", QuickAccessMessages.QuickAccess_StartTypingToFindMatches, table.getItem(0).getText(1));

		text.setText("T");
		processEventsUntil(new Condition() {
			@Override
			public boolean compute() {
				return table.getItemCount() > 1;
			};
		}, 200);
		final int oldCount = table.getItemCount();
		assertTrue("Not enough quick access items for simple filter", oldCount > 3);
		assertTrue("Too many quick access items for size of table", oldCount < 30);
		final String oldFirstItemText = table.getItem(0).getText(1);

		handlerService
		.executeCommand("org.eclipse.ui.window.quickAccess", null); //$NON-NLS-1$
		processEventsUntil(new Condition() {
			@Override
			public boolean compute() {
				return table.getItemCount() != oldCount;
			};
		}, 200);
		final int newCount = table.getItemCount();
		assertTrue("Turning on show all should display more items", newCount > oldCount);
		assertEquals("Turning on show all should not change the top item", oldFirstItemText, table.getItem(0).getText(1));

		handlerService
		.executeCommand("org.eclipse.ui.window.quickAccess", null); //$NON-NLS-1$
		processEventsUntil(new Condition() {
			@Override
			public boolean compute() {
				return table.getItemCount() != newCount;
			};
		}, 200);
		assertTrue("Turning off show all should limit items shown", table.getItemCount() < newCount);
		assertEquals("Turning off show all should not change the top item", oldFirstItemText, table.getItem(0).getText(1));

		handlerService
		.executeCommand("org.eclipse.ui.window.quickAccess", null); //$NON-NLS-1$
		processEventsUntil(new Condition() {
			@Override
			public boolean compute() {
				return table.getItemCount() != oldCount;
			};
		}, 200);
		assertEquals("Turning on show all twice shouldn't change the items", newCount, table.getItemCount());
		assertEquals("Turning on show all twice shouldn't change the top item", oldFirstItemText, table.getItem(0).getText(1));

		shell.setVisible(false);
		handlerService
		.executeCommand("org.eclipse.ui.window.quickAccess", null); //$NON-NLS-1$
		text.setText("T");
		processEventsUntil(new Condition() {
			@Override
			public boolean compute() {
				return table.getItemCount() > 1;
			};
		}, 200);
		assertTrue("Show all should be turned off when the shell is closed and reopened", table.getItemCount() < newCount);
	}

}
