package org.eclipse.ui.tests.api;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IPerspectiveRegistry;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.IWorkingSetManager;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.tests.harness.util.ArrayUtil;
import org.eclipse.ui.tests.harness.util.EmptyPerspective;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class IWorkbenchTest extends UITestCase {

    public IWorkbenchTest(String testName) {
        super(testName);
    }

    public void XXXtestGetActiveWorkbenchWindow() throws Throwable {
        IWorkbenchWindow win1, win2;

        win1 = fWorkbench.getActiveWorkbenchWindow();
        assertNotNull(win1);

        win1 = openTestWindow();
        assertEquals(win1, fWorkbench.getActiveWorkbenchWindow());

        win2 = openTestWindow();
        assertEquals(win2, fWorkbench.getActiveWorkbenchWindow());

        win1.getShell().forceFocus();
        processEvents();
        assertEquals(win1, fWorkbench.getActiveWorkbenchWindow());

        win2.getShell().forceFocus();
        processEvents();
        assertEquals(win2, fWorkbench.getActiveWorkbenchWindow());

    }

    public void testGetEditorRegistry() throws Throwable {
        IEditorRegistry reg = fWorkbench.getEditorRegistry();
        assertNotNull(reg);
    }

    public void testGetPerspectiveRegistry() throws Throwable {
        IPerspectiveRegistry reg = fWorkbench.getPerspectiveRegistry();
        assertNotNull(reg);
    }

    public void testGetPrefereneManager() throws Throwable {
        PreferenceManager mgr = fWorkbench.getPreferenceManager();
        assertNotNull(mgr);
    }

    public void testGetSharedImages() throws Throwable {
        ISharedImages img = fWorkbench.getSharedImages();
        assertNotNull(img);
    }

    public void testGetWorkingSetManager() throws Throwable {
        IWorkingSetManager workingSetManager = fWorkbench
                .getWorkingSetManager();
        IWorkspace workspace = ResourcesPlugin.getWorkspace();

        assertNotNull(workingSetManager);

        IWorkingSet workingSet = workingSetManager.createWorkingSet("ws1",
                new IAdaptable[] { workspace.getRoot() });
        workingSetManager.addWorkingSet(workingSet);
        workingSetManager = fWorkbench.getWorkingSetManager();
        assertEquals(1, workingSetManager.getWorkingSets().length);
        assertEquals(workingSet, workingSetManager.getWorkingSets()[0]);
        
        workingSetManager.removeWorkingSet(workingSet);
    }

    public void testGetWorkbenchWindows() throws Throwable {
        IWorkbenchWindow[] wins = fWorkbench.getWorkbenchWindows();
        assertEquals(ArrayUtil.checkNotNull(wins), true);
        int oldTotal = wins.length;
        int num = 3;

        IWorkbenchWindow[] newWins = new IWorkbenchWindow[num];
        for (int i = 0; i < num; i++)
            newWins[i] = openTestWindow();

        wins = fWorkbench.getWorkbenchWindows();
        for (int i = 0; i < num; i++)
            assertTrue(ArrayUtil.contains(wins, newWins[i]));

        assertEquals(wins.length, oldTotal + num);

        closeAllTestWindows();
        wins = fWorkbench.getWorkbenchWindows();
        assertEquals(wins.length, oldTotal);
    }

    public void XXXtestOpenWorkbenchWindow() throws Throwable {
        IWorkbenchWindow win = null;
        try {
            win = fWorkbench.openWorkbenchWindow(EmptyPerspective.PERSP_ID, getPageInput());
            assertNotNull(win);
            assertEquals(win, fWorkbench.getActiveWorkbenchWindow());
            assertEquals(EmptyPerspective.PERSP_ID, win.getActivePage()
                    .getPerspective().getId());
        } finally {
            if (win != null)
                win.close();
        }

        boolean exceptionOccured = false;
        try {
            win = fWorkbench.openWorkbenchWindow("afdasfdasf", getPageInput());
        } catch (WorkbenchException ex) {
            exceptionOccured = true;
        }

        assertEquals(exceptionOccured, true);
    }

    public void XXXtestOpenWorkbenchWindow2() throws Throwable {
        IWorkbenchWindow win = null;

        try {
            win = fWorkbench
                    .openWorkbenchWindow(getPageInput());
            assertNotNull(win);
			assertEquals(win, fWorkbench.getActiveWorkbenchWindow());
            String defaultID = fWorkbench.getPerspectiveRegistry()
                    .getDefaultPerspective();
            assertEquals(win.getActivePage().getPerspective().getId(),
                    defaultID);

        } finally {
            if (win != null)
                win.close();
        }
    }

    public void testOpenPage1() throws Throwable {
        IWorkbenchWindow win = null;
        try {
             win = fWorkbench.openWorkbenchWindow(ResourcesPlugin.getWorkspace());
             assertNotNull(win);

             IPreferenceStore store = WorkbenchPlugin.getDefault().getPreferenceStore();
             store.setValue(IPreferenceConstants.REUSE_PERSPECTIVES, 
             true);
             
             page1 = fWorkbench.openPage(ResourcesPlugin.getWorkspace());
             assertNotNull(page1);
             page2 = fWorkbench.openPage(ResourcesPlugin.getWorkspace());
             assertNotNull(page2);
             assertEquals("Pages should be equal", page1, page2);

             store.setValue(IPreferenceConstants.REUSE_PERSPECTIVES, 
             false);
             page1 = fWorkbench.openPage(ResourcesPlugin.getWorkspace());
             assertNotNull(page1);
             page2 = fWorkbench.openPage(ResourcesPlugin.getWorkspace());
             assertNotNull(page2);
             assertTrue("Pages should be not equal", page1 != page2);
        } finally {
            if (win != null)
                win.close();
        }
    }

    public void testOpenPage2() throws Throwable {
        IWorkbenchWindow win = null;
        try {
             win = fWorkbench.openWorkbenchWindow(ResourcesPlugin.getWorkspace());
             assertNotNull(win);

             IPreferenceStore store = WorkbenchPlugin.getDefault().getPreferenceStore();
             store.setValue(IPreferenceConstants.REUSE_PERSPECTIVES, 
             true);
             
             page1 = fWorkbench.openPage(EmptyPerspective.PERSP_ID,
             ResourcesPlugin.getWorkspace(), 0);
             assertNotNull(page1);
             page2 = fWorkbench.openPage(IWorkbenchConstants.DEFAULT_LAYOUT_ID,
             ResourcesPlugin.getWorkspace(), 0);
             assertNotNull(page2);
             assertEquals("Pages should be equal", page1, page2);

             store.setValue(IPreferenceConstants.REUSE_PERSPECTIVES, 
             false);
             
             page1 = fWorkbench.openPage(EmptyPerspective.PERSP_ID,
             ResourcesPlugin.getWorkspace(), 0);
             assertNotNull(page1);
             page2 = fWorkbench.openPage(IWorkbenchConstants.DEFAULT_LAYOUT_ID,
             ResourcesPlugin.getWorkspace(), 0);
             assertTrue("Pages should be not equal", page1 != page2);
        } finally {
            if (win != null)
                win.close();
        }
    }

    public void testClose() {
    }
}
