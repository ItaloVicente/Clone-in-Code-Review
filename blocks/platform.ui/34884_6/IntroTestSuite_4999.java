package org.eclipse.ui.tests.intro;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.intro.IIntroConstants;
import org.eclipse.ui.internal.intro.IntroDescriptor;
import org.eclipse.ui.internal.util.PrefUtil;
import org.eclipse.ui.intro.IIntroPart;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class IntroTest2 extends UITestCase {

    IWorkbenchWindow window = null;

    private IntroDescriptor oldDesc;

    public IntroTest2(String testName) {
        super(testName);
    }
    
    public void testPerspectiveChangeWith33StickyBehavior() {
    	IWorkbench workbench = window.getWorkbench();
        IIntroPart part = workbench.getIntroManager().showIntro(window, false);
        assertNotNull(part);
        IWorkbenchPage activePage = window.getActivePage();
        IPerspectiveDescriptor oldDesc = activePage.getPerspective();
        activePage.setPerspective(WorkbenchPlugin.getDefault()
                .getPerspectiveRegistry().findPerspectiveWithId(
                        "org.eclipse.ui.tests.api.SessionPerspective"));
       
        IViewPart viewPart = window.getActivePage().findView(
				IIntroConstants.INTRO_VIEW_ID);
        assertNotNull(viewPart);
        
        window.getActivePage().hideView(viewPart);
        viewPart = window.getActivePage().findView(
				IIntroConstants.INTRO_VIEW_ID);
        assertNull(viewPart);
        
        activePage.setPerspective(oldDesc);
        viewPart = window.getActivePage().findView(
				IIntroConstants.INTRO_VIEW_ID);
        assertNull(viewPart);
        
    }

    @Override
	protected void doSetUp() throws Exception {
        super.doSetUp();
        
    	IPreferenceStore preferenceStore = PrefUtil.getAPIPreferenceStore();
    	preferenceStore.putValue(IWorkbenchPreferenceConstants.ENABLE_32_STICKY_CLOSE_BEHAVIOR, "false");
    	
        oldDesc = Workbench.getInstance().getIntroDescriptor();
        IntroDescriptor testDesc = (IntroDescriptor) WorkbenchPlugin
                .getDefault().getIntroRegistry().getIntro(
                        "org.eclipse.ui.testintro");
        Workbench.getInstance().setIntroDescriptor(testDesc);
        window = openTestWindow();
    }

    @Override
	protected void doTearDown() throws Exception {
        super.doTearDown();
        Workbench.getInstance().setIntroDescriptor(oldDesc);
    }
}
