package org.eclipse.ui.tests.menus;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.mapping.ResourceMapping;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.SubContributionItem;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.PartSite;
import org.eclipse.ui.internal.PluginActionContributionItem;
import org.eclipse.ui.internal.PopupMenuExtender;
import org.eclipse.ui.internal.WorkbenchWindow;
import org.eclipse.ui.tests.harness.util.UITestCase;
import org.eclipse.ui.tests.menus.ObjectContributionClasses.ICommon;

public final class ObjectContributionTest extends UITestCase {

	public ObjectContributionTest(final String name) {
		super(name);
	}

	public final void testObjectStateContentType() throws CoreException {
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IProject testProject = workspace.getRoot().getProject(
				"ObjectContributionTestProject");
		testProject.create(null);
		testProject.open(null);
		final IFile xmlFile = testProject.getFile("ObjectContributionTest.xml");
		final String contents = "<testObjectStateContentTypeElement></testObjectStateContentTypeElement>";
		final ByteArrayInputStream inputStream = new ByteArrayInputStream(
				contents.getBytes());
		xmlFile.create(inputStream, true, null);
		final ISelection selection = new StructuredSelection(xmlFile);
		assertPopupMenus(
				"1",
				new String[] { "org.eclipse.ui.tests.testObjectStateContentType" },
				selection, null, true);
	}

	public final void testContributorResourceAdapter() throws CoreException {

		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IProject testProject = workspace.getRoot().getProject(
				ObjectContributionClasses.PROJECT_NAME);
		if (!testProject.exists()) {
			testProject.create(null);
		}
		if (!testProject.isOpen()) {
			testProject.open(null);
		}

		assertPopupMenus(
				"1",
				new String[] { "IResource.1" },
				new StructuredSelection(
						new Object[] { new ObjectContributionClasses.CResource() }),
				IResource.class, true);
		assertPopupMenus(
				"2",
				new String[] { "IProject.1" },
				new StructuredSelection(
						new Object[] { new ObjectContributionClasses.CFile() }),
				null, false);
		assertPopupMenus(
				"3",
				new String[] { "IFile.1" },
				new StructuredSelection(
						new Object[] { new ObjectContributionClasses.CFile() }),
				IFile.class, true);
		assertPopupMenus("4", new String[] { "IResource.1" },
				new StructuredSelection(new Object[] {
						new ObjectContributionClasses.CFile(),
						new ObjectContributionClasses.CResource() }),
				IResource.class, true);
		assertPopupMenus("5", new String[] { "IFile.1", "IProject.1" },
				new StructuredSelection(new Object[] {
						new ObjectContributionClasses.CFile(),
						new ObjectContributionClasses.CResource() }),
				IResource.class, false);
		assertPopupMenus("6", new String[] { "ResourceMapping.1" },
				new StructuredSelection(new Object[] {
						new ObjectContributionClasses.CFile(),
						new ObjectContributionClasses.CResource() }),
				ResourceMapping.class, true);
		assertPopupMenus(
				"7",
				new String[] { "ResourceMapping.1", "IResource.1" },
				new StructuredSelection(
						new Object[] { new ObjectContributionClasses.ModelElement() }),
				ResourceMapping.class, true);
		assertPopupMenus(
				"8",
				new String[] { "ResourceMapping.1", "IResource.1" },
				new StructuredSelection(
						new Object[] { new ObjectContributionClasses.CResourceOnly() }),
				ResourceMapping.class, true);
	}

	public final void testAdaptables() {
		assertPopupMenus("1", new String[] { "ICommon.1" },
				new StructuredSelection(
						new Object[] { new ObjectContributionClasses.A() }),
				ICommon.class, true);
		assertPopupMenus("2", new String[] { "ICommon.1" },
				new StructuredSelection(new Object[] {
						new ObjectContributionClasses.A(),
						new ObjectContributionClasses.B() }), ICommon.class,
				true);
		assertPopupMenus("3", new String[] { "ICommon.1" },
				new StructuredSelection(new Object[] {
						new ObjectContributionClasses.A(),
						new ObjectContributionClasses.B(),
						new ObjectContributionClasses.C(),
						new ObjectContributionClasses.Common() }),
				ICommon.class, true);
		assertPopupMenus("4", new String[] { "ICommon.1" },
				new StructuredSelection(new Object[] {
						new ObjectContributionClasses.Common(),
						new ObjectContributionClasses.C(),
						new ObjectContributionClasses.B(),
						new ObjectContributionClasses.A() }), ICommon.class,
				true);
		assertPopupMenus("5", new String[] { "ICommon.1" },
				new StructuredSelection(new Object[] {
						new ObjectContributionClasses.Common(),
						new ObjectContributionClasses.C(),
						new ObjectContributionClasses.B(),
						new ObjectContributionClasses.C(),
						new ObjectContributionClasses.A(),
						new ObjectContributionClasses.Common() }),
				ICommon.class, true);
		assertPopupMenus("6", new String[] { "ICommon.1" },
				new StructuredSelection(new Object[] {
						new ObjectContributionClasses.C(),
						new ObjectContributionClasses.Common() }),
				ICommon.class, true);
		assertPopupMenus("7", new String[] { "ICommon.1" },
				new StructuredSelection(new Object[] { new Object() }),
				ICommon.class, false);
		assertPopupMenus("8", new String[] { "ICommon.1" },
				new StructuredSelection(new Object[] {
						new ObjectContributionClasses.C(), new Object() }),
				ICommon.class, false);
		assertPopupMenus("9", new String[] { "ICommon.1" },
				new StructuredSelection(new Object[] {
						new ObjectContributionClasses.C(),
						new ObjectContributionClasses.A(), new Object() }),
				ICommon.class, false);
	}

	public final void testDuplicateAdaptables() {
		assertPopupMenus("1", new String[] { "ICommon.1" },
				new StructuredSelection(
						new Object[] { new ObjectContributionClasses.D() }),
				ICommon.class, true);
		assertPopupMenus("1", new String[] { "ICommon.1" },
				new StructuredSelection(
						new Object[] { new ObjectContributionClasses.D() }),
				ICommon.class, true);
		assertPopupMenus("2", new String[] { "ICommon.1" },
				new StructuredSelection(new Object[] {
						new ObjectContributionClasses.D(),
						new ObjectContributionClasses.A() }), ICommon.class,
				true);
		assertPopupMenus("3", new String[] { "ICommon.1" },
				new StructuredSelection(new Object[] {
						new ObjectContributionClasses.A(),
						new ObjectContributionClasses.D() }), ICommon.class,
				true);
		assertPopupMenus("4", new String[] { "ICommon.1" },
				new StructuredSelection(new Object[] {
						new ObjectContributionClasses.Common(),
						new ObjectContributionClasses.D() }), ICommon.class,
				true);
		assertPopupMenus("5", new String[] { "ICommon.1" },
				new StructuredSelection(new Object[] {
						new ObjectContributionClasses.D(),
						new ObjectContributionClasses.Common() }),
				ICommon.class, true);
	}

	public final void testNonAdaptableContributions() {
		assertPopupMenus("1", new String[] { "ICommon.2" },
				new StructuredSelection(new Object[] {
						new ObjectContributionClasses.A(),
						new ObjectContributionClasses.B() }), ICommon.class,
				false);
		assertPopupMenus("2", new String[] { "ICommon.2" },
				new StructuredSelection(new Object[] {
						new ObjectContributionClasses.D(),
						new ObjectContributionClasses.C(),
						new ObjectContributionClasses.Common() }),
				ICommon.class, true);
		assertPopupMenus("3", new String[] { "Common.2" },
				new StructuredSelection(new Object[] {
						new ObjectContributionClasses.D(),
						new ObjectContributionClasses.C(),
						new ObjectContributionClasses.A() }), ICommon.class,
				false);
		assertPopupMenus("4", new String[] { "Common.2" },
				new StructuredSelection(new Object[] {
						new ObjectContributionClasses.B(),
						new ObjectContributionClasses.C(),
						new ObjectContributionClasses.A() }), ICommon.class,
				false);
	}

	public void assertPopupMenus(String name, String[] commandIds,
			final ISelection selection, Class selectionType, boolean existance) {
		ISelectionProvider selectionProvider = new ISelectionProvider() {
			@Override
			public void addSelectionChangedListener(
					ISelectionChangedListener listener) {
			}

			@Override
			public ISelection getSelection() {
				return selection;
			}

			@Override
			public void removeSelectionChangedListener(
					ISelectionChangedListener listener) {
			}

			@Override
			public void setSelection(ISelection selection) {
			}
		};

		final WorkbenchWindow window = (WorkbenchWindow) PlatformUI
				.getWorkbench().getActiveWorkbenchWindow();
		final IWorkbenchPage page = window.getActivePage();
		IWorkbenchPart part = page.getActivePartReference().getPart(true);

		final MenuManager fakeMenuManager = new MenuManager();
		fakeMenuManager.add(new GroupMarker(
				org.eclipse.ui.IWorkbenchActionConstants.MB_ADDITIONS));
		final PopupMenuExtender extender = new PopupMenuExtender(null,
				fakeMenuManager, selectionProvider, part, ((PartSite)part.getSite()).getContext());


		Shell windowShell = window.getShell();
		Menu contextMenu = fakeMenuManager.createContextMenu(windowShell);

		Event showEvent = new Event();
		showEvent.widget = contextMenu;
		showEvent.type = SWT.Show;

		contextMenu.notifyListeners(SWT.Show, showEvent);

		Event hideEvent = new Event();
		hideEvent.widget = contextMenu;
		hideEvent.type = SWT.Hide;

		contextMenu.notifyListeners(SWT.Hide, hideEvent);

		try {
			final IContributionItem[] items = fakeMenuManager.getItems();
			Set seenCommands = new HashSet(Arrays.asList(commandIds));
			List commands = new ArrayList(Arrays.asList(commandIds));
			for (int i = 0; i < items.length; i++) {
				IContributionItem contributionItem = items[i];
				if (selectionType != null) {
					IContributionItem item = contributionItem;
					if (item instanceof SubContributionItem) {
						item = ((SubContributionItem) contributionItem)
								.getInnerItem();
					}
					if (item instanceof PluginActionContributionItem) {
						ISelection s = null;
						if (s instanceof IStructuredSelection) {
							for (Iterator it = ((IStructuredSelection) s)
									.iterator(); it.hasNext();) {
								Object element = it.next();
								assertTrue(name + " selection not converted",
										selectionType.isInstance(element));
							}
						}
					}
				}
				String id = contributionItem.getId();
				if (existance) {
					boolean removed = commands.remove(id);
					if (seenCommands.contains(id) && !removed) {
						fail(name + " item duplicated in the context menu: "
								+ id);
					}
				} else {
					assertTrue(
							name + " item should not be in the context menu",
							!commands.contains(id));
				}
			}

			if (existance && !commands.isEmpty()) {
				fail(name + " Missing " + commands.toString()
						+ " from context menu.");
			}
		} finally {
			extender.dispose();
			contextMenu.dispose();
		}
	}
}
