
package org.eclipse.ui.tests.quickaccess;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.model.application.ui.menu.MToolControl;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.internal.WorkbenchWindow;
import org.eclipse.ui.internal.quickaccess.SearchField;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class SearchFieldTest extends UITestCase {

	private SearchField searchField;
	private WorkbenchWindow workbenchWindow;

	public SearchFieldTest(String testName) {
		super(testName);
	}

	private void createSearchField() {
		openTestWindow();
		workbenchWindow = (WorkbenchWindow) getWorkbench()
				.getActiveWorkbenchWindow();
		MWindow window = workbenchWindow.getModel();
		EModelService modelService = window.getContext().get(
				EModelService.class);
		MToolControl control = (MToolControl) modelService.find(
				"SearchField", window); //$NON-NLS-1$
		searchField = (SearchField) control.getObject();
		assertNotNull("Search Field must exist", searchField);
	}

	public void testPreviousChoicesAvailable() throws InvocationTargetException, InterruptedException {
		createSearchField();
		Shell shell = searchField.getQuickAccessShell();
		assertFalse("Quick access dialog should not be visible yet", shell.isVisible());
		Text text = searchField.getQuickAccessSearchText();
		String quickAccessElementText = "Project Explorer";
		text.setText(quickAccessElementText);
		processEventsUntil(() -> getAllEntries(searchField.getQuickAccessTable()).get(0).toLowerCase()
				.contains(quickAccessElementText.toLowerCase()), 200);
		searchField.getQuickAccessTable().select(0);
		Event enterPressed = new Event();
		enterPressed.widget = text;
		enterPressed.keyCode = SWT.CR;
		text.notifyListeners(SWT.KeyDown, enterPressed);
		workbenchWindow.close(true);
		processEventsUntil(() -> searchField.getQuickAccessSearchText().isDisposed(), 500);
		createSearchField();
		searchField.activate(searchField.getQuickAccessSearchText());
		processEventsUntil(() -> searchField.getQuickAccessTable().getItemCount() > 1, 500);
		assertTrue(getAllEntries(searchField.getQuickAccessTable()).get(0).contains(quickAccessElementText));
	}

	private List<String> getAllEntries(Table table) {
		final int nbColumns = table.getColumnCount();
		return Arrays.stream(table.getItems()).map(item -> {
			StringBuilder res = new StringBuilder();
			for (int i = 0; i < nbColumns; i++) {
				res.append(item.getText(i));
				res.append(" | ");
			}
			return res.toString();
		}).collect(Collectors.toList());
	}

}
