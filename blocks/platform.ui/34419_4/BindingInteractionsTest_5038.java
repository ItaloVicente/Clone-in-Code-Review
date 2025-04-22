package org.eclipse.ui.tests.intro;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.internal.intro.IntroDescriptor;
import org.eclipse.ui.intro.IIntroPart;
import org.eclipse.ui.tests.api.IWorkbenchPartTest;
import org.eclipse.ui.tests.api.MockPart;

public class NoIntroPartTest extends IWorkbenchPartTest {

    private IntroDescriptor oldDesc;

    public NoIntroPartTest(String testName) {
        super(testName);
    }

    @Override
	protected MockPart openPart(IWorkbenchPage page) throws Throwable {
        return (MockPart) page.getWorkbenchWindow().getWorkbench()
                .getIntroManager().showIntro(page.getWorkbenchWindow(), false);
    }

    @Override
	protected void closePart(IWorkbenchPage page, MockPart part)
            throws Throwable {
        assertTrue(page.getWorkbenchWindow().getWorkbench().getIntroManager()
                .closeIntro((IIntroPart) part));
    }

    @Override
	public void testOpenAndClose() throws Throwable {
        MockPart part = openPart(fPage);
        assertNull(part);
    }

    @Override
	protected void doSetUp() throws Exception {
        super.doSetUp();
        oldDesc = Workbench.getInstance().getIntroDescriptor();
        Workbench.getInstance().setIntroDescriptor(null);
    }

    @Override
	protected void doTearDown() throws Exception {
        super.doTearDown();
        Workbench.getInstance().setIntroDescriptor(oldDesc);
    }

}
