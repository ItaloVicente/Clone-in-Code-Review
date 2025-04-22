
package org.eclipse.ui.tests.api;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.intro.IIntroPart;
import org.eclipse.ui.testing.IWorkbenchPartTestable;
import org.eclipse.ui.tests.harness.util.FileUtil;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class IWorkbenchPartTestableTests extends UITestCase {

	public IWorkbenchPartTestableTests(String testName) {
		super(testName);
	}

	public void XXXtestGetComposite() throws CoreException {
		IWorkbenchPage page = openTestWindow(IDE.RESOURCE_PERSPECTIVE_ID)
				.getActivePage();
		assertNotNull(page);

		IProject proj = FileUtil.createProject("testIWorkbenchPartTestable");

		assertNotNull(IDE
				.openEditor(page, FileUtil.createFile("foo.txt", proj)));
		assertNotNull(IDE.openEditor(page, FileUtil.createFile(
				"foo.properties", proj)));
		assertNotNull(IDE.openEditor(page, FileUtil
				.createFile("foo.java", proj)));
		assertNotNull(IDE
				.openEditor(page, FileUtil.createFile("foo.xml", proj)));

		IEditorPart editors[] = page.getEditors();
		Set encounteredControls = new HashSet();
		testParts(editors, encounteredControls);

		IViewPart views[] = page.getViews();
		testParts(views, encounteredControls);

		IIntroPart intro = page.getWorkbenchWindow().getWorkbench()
				.getIntroManager().showIntro(page.getWorkbenchWindow(), false);
		testParts(new IIntroPart [] { intro }, encounteredControls);

		encounteredControls.clear();
	}

	private void testParts(Object[] parts, Set encounteredControls) {
		for (Object part : parts) {
			String title = null;
			IWorkbenchPartTestable testable = null;
			if (parts instanceof IWorkbenchPart[]) {
				testable = (IWorkbenchPartTestable) ((IWorkbenchPart) part)
						.getSite().getAdapter(IWorkbenchPartTestable.class);
				title = ((IWorkbenchPart) part).getTitle();
			} else {
				testable = (IWorkbenchPartTestable) ((IIntroPart) part)
						.getIntroSite()
						.getAdapter(IWorkbenchPartTestable.class);
				title = ((IIntroPart) part).getTitle();
			}
			assertNotNull(title + " has null testable", testable);
			assertTrue(title + " has previously encountered control",
					encounteredControls.add(testable.getControl()));
		}
	}
}
