package org.eclipse.ui.tests.views.properties.tabbed.override.folders;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.tests.views.properties.tabbed.model.Element;
import org.eclipse.ui.tests.views.properties.tabbed.override.OverrideTestsSelection;
import org.eclipse.ui.tests.views.properties.tabbed.override.OverrideTestsView;
import org.eclipse.ui.tests.views.properties.tabbed.override.items.IOverrideTestsItem;

public class OverrideTestsTabFolderPropertySheetPageContentManager implements
		IOverrideTestsTabItemSelectionListener {

	private IOverrideTestsTabFolder activeFolder;

	private Composite composite;

	private IOverrideTestsTabFolder emptyFolder;

	private IOverrideTestsTabFolder[] folders;

	private OverrideTestsView overrideTestsView;

	public OverrideTestsTabFolderPropertySheetPageContentManager(
			Composite parent) {
		this.composite = parent;
		this.folders = new IOverrideTestsTabFolder[] { new BasicTabFolder(),
				new AdvancedTabFolder() };
		this.emptyFolder = new EmptyTabFolder();
	}

	public void itemSelected(IOverrideTestsItem item) {
		overrideTestsView.setSelection(item.getElement());
	}

	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		Assert.isTrue(part instanceof OverrideTestsView);
		this.overrideTestsView = (OverrideTestsView) part;
		Assert.isTrue(selection instanceof OverrideTestsSelection);
		Element element = ((OverrideTestsSelection) selection).getElement();
		IOverrideTestsTabFolder newFolder = null;

		if (element == null) {
			newFolder = emptyFolder;
		} else {
			for (int i = 0; i < folders.length; i++) {
				if (folders[i].appliesTo(element)) {
					newFolder = folders[i];
					break;
				}
			}
		}

		Assert.isTrue(newFolder != null);
		if (newFolder != activeFolder) {
			if (activeFolder != null) {
				activeFolder.removeItemSelectionListener(this);
				activeFolder.dispose();
			}
			activeFolder = newFolder;
			newFolder.createControls(composite);
			composite.layout(true);
			activeFolder.addItemSelectionListener(this);
		}
		if (element != null) {
			activeFolder.selectionChanged(element);
		}

	}
}
