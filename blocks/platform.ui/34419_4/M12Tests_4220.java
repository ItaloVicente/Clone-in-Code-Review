package org.eclipse.ui.tests.navigator;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.tests.harness.util.DisplayHelper;
import org.eclipse.ui.tests.navigator.m12.M1ContentProvider;
import org.eclipse.ui.tests.navigator.m12.M2ContentProvider;
import org.eclipse.ui.tests.navigator.m12.model.M1Project;
import org.eclipse.ui.tests.navigator.m12.model.M2File;
import org.junit.Test;

public class M12Tests extends NavigatorTestBase {

	private static final boolean SLEEP_LONG = false;

	public M12Tests() {
		_navigatorInstanceId = TEST_CONTENT_M12_VIEW;
	}

	private void _initContent() {
		String[] EXTENSIONS = new String[] { COMMON_NAVIGATOR_RESOURCE_EXT,
				TEST_CONTENT_M12_M1_CONTENT, TEST_CONTENT_M12_M2_CONTENT };
		_contentService.bindExtensions(EXTENSIONS, false);
		_contentService.getActivationService().activateExtensions(EXTENSIONS,
				true);
	}

	@Test
	public void testM1ChildrenAreThere() throws Exception {
		_initContent();

		TreeItem[] rootItems = _viewer.getTree().getItems();
		_expand(rootItems);
		TreeItem p1Item = rootItems[_p1Ind];

		assertEquals("P1 tree item should be an M1Project", M1Project.class,
				p1Item.getData().getClass());

		TreeItem[] p1Children = p1Item.getItems();
		_expand(p1Children);

		TreeItem f1Child = _findChild("f1", p1Children);
		assertNotNull("P1 should have a child named f1", f1Child);

		TreeItem[] f1Children = f1Child.getItems();
		assertEquals("[bug #285353] f1 folder should have 2 children", 2,
				f1Children.length);
	}

	@Test
	public void testM1ChildrenAreThereWithoutM2() throws Exception {
		String[] EXTENSIONS = new String[] { COMMON_NAVIGATOR_RESOURCE_EXT,
				TEST_CONTENT_M12_M1_CONTENT };
		_contentService.bindExtensions(EXTENSIONS, false);
		_contentService.getActivationService().activateExtensions(EXTENSIONS,
				true);

		TreeItem[] rootItems = _viewer.getTree().getItems();
		_expand(rootItems);

		TreeItem[] p1Children = rootItems[_p1Ind].getItems();
		_expand(p1Children);

		TreeItem f1Child = _findChild("f1", p1Children);

		assertNotNull("P1 should have a child named f1", f1Child);

		TreeItem[] f1Children = f1Child.getItems();
		assertEquals("f1 folder should have 2 children", 2, f1Children.length);
	}

	@Test
	public void testM2Override() throws Exception {
		_initContent();

		TreeItem[] rootItems = _viewer.getTree().getItems();
		_expand(rootItems);
		TreeItem p2Item = _findChild("p2", rootItems);

		TreeItem[] p2Children = p2Item.getItems();
		_expand(p2Children);

		if (SLEEP_LONG)
			DisplayHelper.sleep(10000000);

		TreeItem file2Child = _findChild("file2.txt", p2Children);
		assertNotNull("P2 should have a child named file2.txt", file2Child);
		assertEquals("file2.txt should be provided by M2 content provider",
				M2File.class, file2Child.getData().getClass());

	}

	@Test
	public void testInterceptAdd() throws CoreException {
		final String NEW_FOLDER_1 = "newFolder1";

		_initContent();

		TreeItem[] rootItems = _viewer.getTree().getItems();
		_expand(rootItems);

		IFolder newFolder1 = _p1.getFolder(NEW_FOLDER_1);
		newFolder1.create(true, true, new NullProgressMonitor());

		TreeItem folder1Item = _findChild(NEW_FOLDER_1, rootItems[_p1Ind]
				.getItems());

		assertNotNull("M1 interceptAdd method should have been called",
				folder1Item);
	}

	public void XXXtestInterceptRemove() throws CoreException {
		final String NEW_FOLDER_1 = "newFolder1";

		_initContent();

		TreeItem[] rootItems = _viewer.getTree().getItems();
		_expand(rootItems);

		IFolder newFolder1 = _p1.getFolder(NEW_FOLDER_1);
		if (!newFolder1.exists()) {
			newFolder1.create(true, true, new NullProgressMonitor());
		}

		TreeItem folder1Item = _findChild(NEW_FOLDER_1, rootItems[_p1Ind]
				.getItems());

		newFolder1.delete(true, new NullProgressMonitor());
		folder1Item = _findChild(NEW_FOLDER_1, rootItems[_p1Ind].getItems());
		assertNull(
				"[bug 285529] M1 interceptRemove method should have been called",
				folder1Item);
	}

	public void XXXtestInterceptRefreshOnChildTypeChange() throws CoreException {
		_initContent();

		final IFile file2 = _p2.getFile("file2.txt");

		IWorkspaceRunnable runnable = new IWorkspaceRunnable() {

			@Override
			public void run(IProgressMonitor monitor) throws CoreException {
				file2.delete(true, new NullProgressMonitor());
				file2.create(null, true, null);
			}
		};

		M1ContentProvider.resetCounters();
		M2ContentProvider.resetCounters();
		ResourcesPlugin.getWorkspace().run(runnable, new NullProgressMonitor());

		assertTrue(
				"[bug 285529] M1 intercept update or refresh should have been called",
				M1ContentProvider.getInterceptRefreshCount()
						+ M1ContentProvider.getInterceptUpdateCount() >= 1);
		assertTrue(
				"[bug 285529] M2 intercept update or refresh should have been called",
				M2ContentProvider.getInterceptRefreshCount()
						+ M2ContentProvider.getInterceptUpdateCount() >= 1);
	}

}
