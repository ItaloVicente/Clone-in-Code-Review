package org.eclipse.ui.tests.api;

import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.XMLMemento;
import org.eclipse.ui.internal.IWorkbenchConstants;
import org.eclipse.ui.internal.WindowTrimProxy;
import org.eclipse.ui.internal.WorkbenchWindow;
import org.eclipse.ui.internal.layout.ITrimManager;
import org.eclipse.ui.internal.layout.IWindowTrim;
import org.eclipse.ui.internal.util.PrefUtil;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class TrimLayoutTest extends UITestCase {

	private static final String BUTTON_B_ID = "my.button.b";

	public static final String[] TOP_TRIM_LIST = {
			"org.eclipse.ui.internal.WorkbenchWindow.topBar",
			TrimList.TRIM_LIST_ID };

	public static final String[] TOP_BUTTON_TRIM = {
			"org.eclipse.ui.internal.WorkbenchWindow.topBar", BUTTON_B_ID,
			TrimList.TRIM_LIST_ID };

	private boolean fHeapStatusPref;

	public TrimLayoutTest(String testName) {
		super(testName);
	}

	public void testGetIDs() throws Throwable {
		WorkbenchWindow window = openWorkbenchWindow();
		ITrimManager trimManager = window.getTrimManager();
		int[] ids = trimManager.getAreaIds();
		assertEquals("number of trim areas", 4, ids.length);
	}

	public void testTrimInformation() throws Throwable {
		WorkbenchWindow window = openWorkbenchWindow();
		ITrimManager trimManager = window.getTrimManager();		
		validateDefaultBottomLayout(trimManager);
	}

	public void testMoveStatusLine() throws Throwable {
		WorkbenchWindow window = openWorkbenchWindow();
		ITrimManager trimManager = window.getTrimManager();		
		validateDefaultBottomLayout(trimManager);
		
		@SuppressWarnings("rawtypes")
		List trim = trimManager.getAreaTrim(SWT.BOTTOM);		
		String id1 = ((IWindowTrim) trim.get(1)).getId();
		String id3 = ((IWindowTrim) trim.get(3)).getId();

		swapPostition(trim, 1, 3);
		trimManager.updateAreaTrim(ITrimManager.BOTTOM, trim, false);
		window.getShell().layout(true, true);

		trim = trimManager.getAreaTrim(ITrimManager.BOTTOM);
		assertTrue("element failed to swap", getIndexOf(trim, id1) == 3);
		assertTrue("element failed to swap", getIndexOf(trim, id3) == 1);
	}

	public void testMoveFastViewBar() throws Throwable {
		WorkbenchWindow window = openWorkbenchWindow();
		ITrimManager trimManager = window.getTrimManager();				
		validateDefaultBottomLayout(trimManager);
		
		@SuppressWarnings("rawtypes")
		List trim = trimManager.getAreaTrim(SWT.BOTTOM);		
		String id0 = ((IWindowTrim) trim.get(0)).getId();
		String id3 = ((IWindowTrim) trim.get(3)).getId();

		swapPostition(trim, 0, 3);
		trimManager.updateAreaTrim(ITrimManager.BOTTOM, trim, false);
		window.getShell().layout(true, true);

		trim = trimManager.getAreaTrim(ITrimManager.BOTTOM);
		assertTrue("element failed to swap", getIndexOf(trim, id0) == 3);
		assertTrue("element failed to swap", getIndexOf(trim, id3) == 0);
	}

	public void testRemoveHeapStatus() throws Throwable {
		WorkbenchWindow window = openWorkbenchWindow();
		ITrimManager trimManager = window.getTrimManager();		
		validateDefaultBottomLayout(trimManager);

		@SuppressWarnings("rawtypes")
		List trim = trimManager.getAreaTrim(ITrimManager.BOTTOM);
		int hsIndex = getIndexOf(trim, "org.eclipse.ui.internal.HeapStatus");
		trim.remove(hsIndex);
		trimManager.updateAreaTrim(ITrimManager.BOTTOM, trim, true);
		window.getShell().layout(true, true);

		trim = trimManager.getAreaTrim(ITrimManager.BOTTOM);
		hsIndex = getIndexOf(trim, "org.eclipse.ui.internal.HeapStatus");
		assertTrue("HeapStatus failed to remove", hsIndex == -1);
	}

	public void testAddExtraTrim() throws Throwable {
		WorkbenchWindow window = openWorkbenchWindow();
		ITrimManager trimManager = window.getTrimManager();
		assertTrue(
				"The window should have it's top banner in place",
				trimManager
						.getTrim("org.eclipse.ui.internal.WorkbenchWindow.topBar") != null);
		
		TrimList trimList = new TrimList(window.getShell());
		trimManager.addTrim(ITrimManager.TOP, trimList);
		window.getShell().layout();

		@SuppressWarnings("rawtypes")
		List trim = trimManager.getAreaTrim(ITrimManager.TOP);
		validatePositions(TOP_TRIM_LIST, trim);
	}

	public void testPlaceExtraTrim() throws Throwable {
		WorkbenchWindow window = openWorkbenchWindow();
		ITrimManager trimManager = window.getTrimManager();
		TrimList trimList = new TrimList(window.getShell());
		trimManager.addTrim(ITrimManager.TOP, trimList);

		Button b = new Button(window.getShell(), SWT.PUSH);
		b.setText("B");
		IWindowTrim buttonTrim = new WindowTrimProxy(b, BUTTON_B_ID,
				"Button B", SWT.TOP | SWT.BOTTOM, false);
		
		IWindowTrim trim = trimManager.getTrim(TrimList.TRIM_LIST_ID);
		assertTrue(trimList == trim);
		trimManager.addTrim(ITrimManager.TOP, buttonTrim, trim);
		window.getShell().layout();

		@SuppressWarnings("rawtypes")
		List topTrim = trimManager.getAreaTrim(ITrimManager.TOP);
		validatePositions(TOP_BUTTON_TRIM, topTrim);
	}

	public void testSaveWorkbenchWindow() throws Throwable {
		WorkbenchWindow window = openWorkbenchWindow();

		XMLMemento state = XMLMemento
				.createWriteRoot(IWorkbenchConstants.TAG_WINDOW);

		IMemento trim = state.getChild(IWorkbenchConstants.TAG_TRIM);
		assertNotNull(trim);

		int[] ids = window.getTrimManager().getAreaIds();
		IMemento[] children = trim
				.getChildren(IWorkbenchConstants.TAG_TRIM_AREA);
		assertTrue("Should have <= " + ids.length + " trim areas",
				children.length <= ids.length);
		assertEquals("Our trim configuration starts with", 2, children.length);
	}

	public void testRestoreStateWithChange() throws Throwable {
		WorkbenchWindow window = openWorkbenchWindow();
		ITrimManager trimManager = window.getTrimManager();
		validateDefaultBottomLayout(trimManager);
		
		int bottomTrimCount = trimManager.getAreaTrim(SWT.BOTTOM).size();
		
		XMLMemento state = XMLMemento
				.createWriteRoot(IWorkbenchConstants.TAG_WINDOW);
		
		
		IMemento trimMemento = state.getChild(IWorkbenchConstants.TAG_TRIM);
		assertNotNull(trimMemento);

		IMemento[] children = trimMemento
				.getChildren(IWorkbenchConstants.TAG_TRIM_AREA);
		int childIdx = 0;
		IMemento bottomTrim = null;
		String bottomId = new Integer(SWT.BOTTOM).toString();

		for (; childIdx < children.length; childIdx++) {
			if (children[childIdx].getID().equals(bottomId)) {
				bottomTrim = children[childIdx];
				break;
			}
		}
		assertNotNull(bottomTrim);

		children = bottomTrim.getChildren(IWorkbenchConstants.TAG_TRIM_ITEM);
		assertEquals(bottomTrimCount, children.length);

		String id0 = children[0].getID();
		String id3 = children[3].getID();
		children[0].putString(IMemento.TAG_ID, id3);
		children[3].putString(IMemento.TAG_ID, id0);

		fail("window.restoreState() was a compile error");

		window.getShell().layout(true, true);

		@SuppressWarnings("rawtypes")
		List trim = trimManager.getAreaTrim(ITrimManager.BOTTOM);
		assertTrue("Restore has wrong layout", getIndexOf(trim, id0) == 3);
		assertTrue("Restore has wrong layout", getIndexOf(trim, id3) == 0);
	}

	public void testRestoreStateWithLocationChange() throws Throwable {
		WorkbenchWindow window = openWorkbenchWindow();
		ITrimManager trimManager = window.getTrimManager();
		validateDefaultBottomLayout(trimManager);
		
		int bottomTrimCount = trimManager.getAreaTrim(SWT.BOTTOM).size();

		XMLMemento state = XMLMemento
				.createWriteRoot(IWorkbenchConstants.TAG_WINDOW);
		
		
		fail("window.saveState() was a compile error");
		
		IMemento trim = state.getChild(IWorkbenchConstants.TAG_TRIM);
		assertNotNull(trim);

		IMemento[] children = trim
				.getChildren(IWorkbenchConstants.TAG_TRIM_AREA);
		int childIdx = 0;
		IMemento bottomTrim = null;
		String bottomId = new Integer(SWT.BOTTOM).toString();
		for (; childIdx < children.length; childIdx++) {
			if (children[childIdx].getID().equals(bottomId)) {
				bottomTrim = children[childIdx];
				break;
			}
		}
		assertNotNull(bottomTrim);

		children = bottomTrim.getChildren(IWorkbenchConstants.TAG_TRIM_ITEM);
		assertEquals(bottomTrimCount, children.length);
		
		String id = children[0].getID();
		children[0].putString(IMemento.TAG_ID, children[3].getID());

		IMemento left = trim.createChild(IWorkbenchConstants.TAG_TRIM_AREA,
				new Integer(SWT.LEFT).toString());
		left.createChild(IWorkbenchConstants.TAG_TRIM_ITEM, id);
		
		
		fail("window.restoreState() was a compile error");

		window.getShell().layout(true, true);

		@SuppressWarnings("rawtypes")
		List windowTrim = trimManager.getAreaTrim(ITrimManager.BOTTOM);
		assertEquals(bottomTrimCount-1, windowTrim.size());

		windowTrim = trimManager.getAreaTrim(ITrimManager.LEFT);
		assertEquals(1, windowTrim.size());
	}

	private WorkbenchWindow openWorkbenchWindow() {
		IWorkbenchWindow iw = openTestWindow();
		assertTrue("Window must be a WorkbenchWindow", (iw instanceof WorkbenchWindow));
		
		return (WorkbenchWindow)iw;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void swapPostition(List trim, int pos1, int pos2) {
		Object tmp = trim.get(pos1);
		trim.set(pos1, trim.get(pos2));
		trim.set(pos2, tmp);
	}

	@SuppressWarnings("rawtypes")
	private int getIndexOf(List trimIds, String id) {
		int index = 0;
		for (Iterator iterator = trimIds.iterator(); iterator.hasNext();) {
			IWindowTrim trim = (IWindowTrim) iterator.next();
			if (id.equals(trim.getId()))
				return index;
			index++;
		}
		
		return -1;
	}
	
	private void validateDefaultBottomLayout(ITrimManager trimManager) {
		@SuppressWarnings("rawtypes")
		List descs = trimManager.getAreaTrim(SWT.BOTTOM);

		assertTrue("Too few trim elements", descs.size() >= 4);

		int fvbIndex = getIndexOf(descs, "org.eclise.ui.internal.FastViewBar");
		assertTrue("Fast View Bar not found", fvbIndex != -1);
		int slIndex = getIndexOf(descs, "org.eclipse.jface.action.StatusLineManager");
		assertTrue("StatusLine not found", slIndex != -1);
		int hsIndex = getIndexOf(descs, "org.eclipse.ui.internal.HeapStatus");
		assertTrue("Heap Status not found", hsIndex != -1);
		int prIndex = getIndexOf(descs, "org.eclipse.ui.internal.progress.ProgressRegion");
		assertTrue("Progress Region not found", prIndex != -1);
		
		assertTrue("Fast View out of position", fvbIndex < slIndex);
		assertTrue("Status Line out of position", slIndex < hsIndex);
		assertTrue("Heap Status out of position", hsIndex < prIndex);
	}
	
	@SuppressWarnings("rawtypes")
	private void validatePositions(String[] expectedIDs, List retrievedIDs) {
		assertEquals("Number of trim items don't match", expectedIDs.length,
				retrievedIDs.size());

		for (int i = 0; i < expectedIDs.length; ++i) {
			assertEquals("Failed for postition " + i, expectedIDs[i],
					((IWindowTrim) retrievedIDs.get(i)).getId());
		}
	}

	@Override
	protected void doSetUp() throws Exception {
		super.doSetUp();
		
		fHeapStatusPref = PrefUtil.getAPIPreferenceStore().getBoolean(
				IWorkbenchPreferenceConstants.SHOW_MEMORY_MONITOR);
		PrefUtil.getAPIPreferenceStore().setValue(
				IWorkbenchPreferenceConstants.SHOW_MEMORY_MONITOR, true);
	}

	@Override
	protected void doTearDown() throws Exception {
		PrefUtil.getAPIPreferenceStore().setValue(
				IWorkbenchPreferenceConstants.SHOW_MEMORY_MONITOR,
				fHeapStatusPref);
		super.doTearDown();
	}

}
