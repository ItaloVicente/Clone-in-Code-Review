package org.eclipse.ui.tests.navigator;

import java.io.ByteArrayInputStream;
import java.util.Properties;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.internal.navigator.NavigatorPlugin;
import org.eclipse.ui.navigator.INavigatorContentDescriptor;
import org.eclipse.ui.tests.harness.util.DisplayHelper;
import org.eclipse.ui.tests.navigator.extension.TestContentProvider;
import org.eclipse.ui.tests.navigator.extension.TestExtensionTreeData;
import org.eclipse.ui.tests.navigator.extension.TestContentProviderResource;
import org.eclipse.ui.tests.navigator.extension.TestSorterDataAndResource;
import org.eclipse.ui.tests.navigator.extension.TestSorterResource;

public class SorterTest extends NavigatorTestBase {

	public SorterTest() {
		_navigatorInstanceId = TEST_VIEWER;
	}

	private int _statusCount;

	public void testSorterMissing() throws Exception {

		TestContentProviderResource._returnBadObject = true;

		_contentService.bindExtensions(
				new String[] { TEST_CONTENT_SORTER_MODEL }, false);
		_contentService.getActivationService().activateExtensions(
				new String[] { TEST_CONTENT_SORTER_MODEL }, true);

		refreshViewer();

		ILogListener ll = new ILogListener() {
			public void logging(IStatus status, String plugin) {
				_statusCount++;
			}
		};

		NavigatorPlugin.getDefault().getLog().addLogListener(ll);

		_viewer.expandAll();

		NavigatorPlugin.getDefault().getLog().removeLogListener(ll);

		assertTrue("Status Count: " + _statusCount, _statusCount == 0);
	}

	public void testSorterProperty() throws Exception {

		_contentService.bindExtensions(
				new String[] { TEST_CONTENT_SORTER_RESOURCE }, false);
		_contentService.getActivationService().activateExtensions(
				new String[] { TEST_CONTENT_SORTER_RESOURCE }, true);

		refreshViewer();

		_viewer.update(_p1, new String[] { "prop1" });
		_viewer.expandAll();
 
		assertEquals("prop1", TestSorterResource._sorterProperty);
		assertEquals(_p1, TestSorterResource._sorterElement);
	}

	protected void dynamicAddModelObjects() throws Exception {
		_viewer.setExpandedState(_project, true);

		Properties props = new Properties();
		props.put("AddedParent", "Child1,AddedChild1");

		TestExtensionTreeData newData = new TestExtensionTreeData(
				TestContentProvider._modelRoot, "AddedParent", props, null);

		_viewer.add(_project, newData);
		_viewer.setExpandedState(newData, true);

		IFile file;

		file = _project.getFile("AddedFile1.txt");
		file.create(new ByteArrayInputStream(new byte[]{}), true, null);
		_viewer.add(newData, file);

		file = _project.getFile("AddedFile2.txt");
		file.create(new ByteArrayInputStream(new byte[]{}), true, null);
		_viewer.add(newData, file);
	}

	public void DISABLED_testSorterContentOverride() throws Exception {
		waitForModelObjects();

		INavigatorContentDescriptor desc = _contentService
		.getContentDescriptorById(TEST_CONTENT_SORTER_RESOURCE_SORTONLY);

		TestSorterResource sorter = (TestSorterResource) _contentService
			.getSorterService().findSorter(desc, _project, null, null);
		sorter._forward = false;

		_contentService.bindExtensions(
				new String[] { TEST_CONTENT_SORTER_RESOURCE_SORTONLY }, false);
		_contentService.getActivationService().activateExtensions(
				new String[] { TEST_CONTENT_SORTER_RESOURCE_SORTONLY }, false);

		_viewer.expandAll();
		TreeItem[] items = _viewer.getTree().getItems();
		TreeItem[] childItems;

		
		assertEquals("p2", items[0].getText());
		assertEquals("p1", items[1].getText());
		assertEquals("Test", items[2].getText());
		assertEquals("Bluefile6.txt", items[0].getItems()[0].getText());
		assertEquals("f2", items[1].getItems()[0].getText());

		_contentService.getActivationService().deactivateExtensions(
				new String[] { TEST_CONTENT_SORTER_RESOURCE_SORTONLY }, false);
		
		refreshViewer();
		_viewer.expandAll();

		final int WAIT_COUNT = 100;
		
		int count = WAIT_COUNT;
		boolean passed = false;

		while (!passed) {
			items = _viewer.getTree().getItems();
			assertEquals("p2", items[1].getText());
			assertEquals("p1", items[0].getText());
			assertEquals("Test", items[2].getText());
			childItems = items[1].getItems();
			if (!childItems[0].getText().equals("f1") && count-- >= 0) {
				System.out.println("Not equal: " + childItems[0].getText() + " waiting...");
				DisplayHelper.sleep(100);
				continue;
			}
			assertEquals("f1", childItems[0].getText());
			childItems = items[0].getItems();
			assertEquals("f1", childItems[0].getText());
			passed = true;
		}

		_contentService.getActivationService().activateExtensions(
				new String[] { TEST_CONTENT_SORTER_RESOURCE_SORTONLY }, false);

		refreshViewer();
		_viewer.expandAll();

		count = WAIT_COUNT;
		passed = false;

		while (!passed) {
			items = _viewer.getTree().getItems();
			assertEquals("p2", items[0].getText());
			assertEquals("p1", items[1].getText());
			assertEquals("Test", items[2].getText());
			if (!items[0].getItems()[0].getText().equals("Bluefile6.txt") && count-- >= 0) {
				System.out
						.println("Not equal: " + items[0].getItems()[0].getText() + " waiting...");
				DisplayHelper.sleep(100);
				continue;
			}
			assertEquals("Bluefile6.txt", items[0].getItems()[0].getText());
			assertEquals("f2", items[1].getItems()[0].getText());
			passed = true;
		}
		_contentService.bindExtensions(
				new String[] { TEST_CONTENT_SORTER_RESOURCE_SORTONLY_OVERRIDE }, false);
		_contentService.getActivationService().activateExtensions(
				new String[] { TEST_CONTENT_SORTER_RESOURCE_SORTONLY_OVERRIDE }, false);

		refreshViewer();
		_viewer.expandAll();

		count = WAIT_COUNT;
		passed = false;

		while (!passed) {
			items = _viewer.getTree().getItems();
			assertEquals("p2", items[2].getText());
			assertEquals("p1", items[1].getText());
			assertEquals("Test", items[0].getText());
			childItems = items[2].getItems();
			if (!childItems[0].getText().equals("f1") && count-- >= 0) {
				System.out.println("Not equal: " + childItems[0].getText() + " waiting...");
				DisplayHelper.sleep(100);
				continue;
			}
			assertEquals("f1", childItems[0].getText());
			childItems = items[1].getItems();
			assertEquals("f1", childItems[0].getText());
			passed = true;
		}
	}

	public void testSorterContentOverrideNoSort() throws Exception {

		waitForModelObjects();

		_contentService.bindExtensions(
				new String[] { TEST_CONTENT_SORTER_MODEL_OVERRIDE_NOSORT }, false);
		_contentService.getActivationService().activateExtensions(
				new String[] { TEST_CONTENT_SORTER_MODEL_OVERRIDE_NOSORT }, false);

		INavigatorContentDescriptor desc = _contentService
				.getContentDescriptorById(TEST_CONTENT_SORTER_MODEL_OVERRIDE);

		TestSorterDataAndResource sorter = (TestSorterDataAndResource) _contentService
				.getSorterService().findSorter(desc, _project, null, null);
		sorter._forward = false;

		_viewer.setExpandedState(_project,	true);

		TreeItem[] items = _viewer.getTree().getItems();

		TreeItem addedParent = items[_projectInd].getItem(0);
		_viewer.setExpandedState(addedParent.getData(), true);
		assertEquals("BlueParent", addedParent.getText());
		assertEquals("BlueChild1", addedParent.getItem(0).getText());
		assertEquals("BlueChild2", addedParent.getItem(1).getText());

	}

	public void testSorterContentAdd() throws Exception {

		waitForModelObjects();

		_contentService.bindExtensions(
				new String[] { TEST_CONTENT_SORTER_MODEL }, false);
		_contentService.getActivationService().activateExtensions(
				new String[] { TEST_CONTENT_SORTER_MODEL }, false);

		dynamicAddModelObjects();

		TreeItem[] items = _viewer.getTree().getItems();

		TreeItem addedParent = items[_projectInd].getItem(0);
		assertEquals("BlueAddedParent", addedParent.getText());
		assertEquals("BlueAddedChild1", addedParent.getItem(0).getText());
		assertEquals("BlueChild1", addedParent.getItem(1).getText());
		assertEquals("BlueAddedFile1.txt", addedParent.getItem(2).getText());
		assertEquals("BlueAddedFile2.txt", addedParent.getItem(3).getText());
	}

	public void testSorterContentAddOverride() throws Exception {

		waitForModelObjects();

		_contentService.bindExtensions(
				new String[] { TEST_CONTENT_SORTER_MODEL_OVERRIDE }, false);
		_contentService.getActivationService().activateExtensions(
				new String[] { TEST_CONTENT_SORTER_MODEL_OVERRIDE }, false);

		dynamicAddModelObjects();

		TreeItem[] items = _viewer.getTree().getItems();

		TreeItem addedParent;
		
		addedParent = items[_projectInd].getItem(3);
		assertEquals("BlueParent", addedParent.getText());
		addedParent = items[_projectInd].getItem(2);
		assertEquals("BlueAddedParent", addedParent.getText());
		
		assertEquals("BlueAddedChild1", addedParent.getItem(0).getText());
		assertEquals("BlueAddedFile1.txt", addedParent.getItem(1).getText());
		assertEquals("BlueAddedFile2.txt", addedParent.getItem(2).getText());
		assertEquals("BlueChild1", addedParent.getItem(3).getText());

	}

	public void testSorterResource() throws Exception {

		_contentService.bindExtensions(
				new String[] { TEST_CONTENT_SORTER_RESOURCE }, false);
		_contentService.getActivationService().activateExtensions(
				new String[] { TEST_CONTENT_SORTER_RESOURCE }, true);

		refreshViewer();

		INavigatorContentDescriptor desc = _contentService
				.getContentDescriptorById(TEST_CONTENT_SORTER_RESOURCE);

		TestSorterResource sorter = (TestSorterResource) _contentService
				.getSorterService().findSorter(desc, _p2, null, null);
		sorter._forward = false;

		IStructuredSelection sel;
		IContainer cont = (IContainer) _p2.members()[1];
		sel = new StructuredSelection(cont.members()[0]);
		_viewer.setSelection(sel);
		_viewer.setExpandedState(cont, true);

		TreeItem[] items = _viewer.getTree().getItems();

		TreeItem file1 = items[2].getItem(0);
		assertEquals("file6.txt", file1.getText());
	}


}
