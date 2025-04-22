package org.eclipse.ui.tests.markers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.jface.dialogs.DialogSettings;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.tests.internal.TestMemento;
import org.eclipse.ui.views.markers.internal.MarkerType;
import org.eclipse.ui.views.markers.internal.ProblemFilter;

public class Bug75909Test extends TestCase {

	private static final int OLD_SETTINGS_SELECTED = 4;

	private static final String REMOVED_MARKER_ID = "org.eclipse.pde.core.problem";

	private static final String INCLUDED_MARKER_ID = "org.eclipse.core.resources.problemmarker";

	private static final String MISSING_MARKER_ID = "org.eclipse.jdt.core.problem";

	private static final String OLD_DIALOG_SETTINGS_XML = "old_dialog_settings.xml";

	public static TestSuite suite() {
		return new TestSuite(Bug75909Test.class);
	}

	public void testBasicFilter() throws Throwable {
		ProblemFilter filter = new ProblemFilter("Bug75909Test");
		filter.resetState();

		List allTypes = new ArrayList();
		filter.addAllSubTypes(allTypes);
		int num_types = allTypes.size();

		assertTrue("There should be more than 4 types in the system",
				num_types > 4);

		assertEquals(num_types, filter.getSelectedTypes().size());
	}


	public void testRestoreOldState() throws Throwable {
		IDialogSettings settings = new DialogSettings("Workbench");
		loadSettings(settings, Bug75909Test.OLD_DIALOG_SETTINGS_XML);

		ProblemFilter filter = new ProblemFilter("Bug75909Test");
		filter.restoreFilterSettings(getFilterSettings(settings));

		List selected = filter.getSelectedTypes();
		assertEquals(Bug75909Test.OLD_SETTINGS_SELECTED, selected.size());

		MarkerType marker = getType(filter, Bug75909Test.INCLUDED_MARKER_ID);
		assertTrue(selected.contains(marker));

		MarkerType removed = getType(filter, Bug75909Test.REMOVED_MARKER_ID);
		assertFalse(selected.contains(removed));
	}

	public void testRestoreNewStateMissingId() throws Throwable {
		IMemento settings = createMissingMemento();

		ProblemFilter filter = new ProblemFilter("Bug75909Test");
		filter.restoreState(settings);

		List included = new ArrayList();
		filter.addAllSubTypes(included);
		
		List selected = filter.getSelectedTypes();
		assertEquals(included.size() - 1, selected.size());

		MarkerType marker = getType(filter, Bug75909Test.INCLUDED_MARKER_ID);
		assertTrue(selected.contains(marker));

		MarkerType removed = getType(filter, Bug75909Test.REMOVED_MARKER_ID);
		assertFalse(selected.contains(removed));

		MarkerType missing = getType(filter, Bug75909Test.MISSING_MARKER_ID);
		assertTrue(selected.contains(missing));
	}
	
	private IMemento createMissingMemento() {
		TestMemento memento = new TestMemento("filter","Filter Test");
		memento.putString("selectBySeverity","false");
		memento.putString("contains","true");
		memento.putString("enabled","true");
		memento.putInteger("severity",0);
		memento.putString("description","");
		memento.putString("filterOnMarkerLimit","true");
		memento.putString("selectionStatus" ,"org.eclipse.core.resources.problemmarker:true:org.eclipse.pde.core.problem:false:org.eclipse.jdt.core.buildpath_problem:true:org.eclipse.ant.ui.buildFileProblem:true:");
		memento.putInteger("onResource",0);
		return memento;
	}

	private IDialogSettings getFilterSettings(IDialogSettings settings) {
		return settings.getSection("filter");
	}

	public void testSaveState() throws Throwable {
		ProblemFilter filter = new ProblemFilter("Bug75909Test");
		filter.resetState();

		List allTypes = new ArrayList();
		filter.addAllSubTypes(allTypes);

		MarkerType removed = getType(filter, Bug75909Test.REMOVED_MARKER_ID);
		
		filter.getSelectedTypes().remove(removed);
		assertEquals(allTypes.size() - 1, filter.getSelectedTypes().size());

		IMemento settings = new TestMemento("Test","Bug75909Test");
		filter.saveFilterSettings(settings);

		ProblemFilter f2 = new ProblemFilter("Bug75909Test");
		f2.restoreState(settings);
		
		assertEquals(filter.getSelectedTypes().size(),
				f2.getSelectedTypes().size());
		assertFalse(f2.getSelectedTypes().contains(removed));
	}


	private void loadSettings(IDialogSettings settings, String resource)
			throws UnsupportedEncodingException, IOException {
		InputStream io = null;
		try {
			io = getClass().getResourceAsStream(resource);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					io, "utf-8"));
			settings.load(reader);
		} finally {
			if (io != null) {
				io.close();
			}
		}
	}

	private MarkerType getType(ProblemFilter filter, String id) {
		return filter.getMarkerType(id);
	}

}
