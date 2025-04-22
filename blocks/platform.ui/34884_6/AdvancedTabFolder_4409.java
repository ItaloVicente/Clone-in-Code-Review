package org.eclipse.ui.tests.views.properties.tabbed.override.folders;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.tests.views.properties.tabbed.model.Element;
import org.eclipse.ui.tests.views.properties.tabbed.override.items.IOverrideTestsItem;

public abstract class AbstractTabFolder implements IOverrideTestsTabFolder {

	private ListenerList itemSelectionListeners = new ListenerList();

	private CTabFolder tabFolder;

	public void addItemSelectionListener(
			IOverrideTestsTabItemSelectionListener listener) {
		itemSelectionListeners.add(listener);
	}

	public boolean appliesTo(Element element) {
		return false;
	}

	public void createControls(Composite composite) {
		tabFolder = new CTabFolder(composite, SWT.NONE);

		IOverrideTestsItem[] items = getItem();

		for (int i = 0; i < items.length; i++) {
			CTabItem tabItem = new CTabItem(tabFolder, SWT.NONE);
			items[i].createControls(tabFolder);
			tabItem.setText(items[i].getText());
			tabItem.setImage(items[i].getImage());
			tabItem.setControl(items[i].getComposite());
			tabItem.setData(items[i]);
		}
		tabFolder.setSelection(0);

		tabFolder.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				CTabItem aTabItem = (CTabItem) e.item;
				Object[] listeners = itemSelectionListeners.getListeners();
				for (int i = 0; i < listeners.length; i++) {
					IOverrideTestsTabItemSelectionListener listener = (IOverrideTestsTabItemSelectionListener) listeners[i];
					listener.itemSelected((IOverrideTestsItem) aTabItem
							.getData());
				}
			}
		});
	}

	public void dispose() {
		tabFolder.dispose();
	}

	public void removeItemSelectionListener(
			IOverrideTestsTabItemSelectionListener listener) {
		itemSelectionListeners.remove(listener);
	}

	public void selectionChanged(Element element) {
		CTabItem[] items = tabFolder.getItems();
		for (int i = 0; i < items.length; i++) {
			CTabItem tabItem = items[i];
			if (((IOverrideTestsItem) tabItem.getData()).getText().equals(
					element.getName())) {
				tabFolder.setSelection(tabItem);
			}
		}
	}

}
