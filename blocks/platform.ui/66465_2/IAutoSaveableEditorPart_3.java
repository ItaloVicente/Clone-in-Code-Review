package org.eclipse.ui.internal.ide.menus;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.internal.IPreferenceConstants;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.menus.WorkbenchWindowControlContribution;

public class AutoSaveStatusBarItem extends WorkbenchWindowControlContribution {

	private ToolBar toolBar;

	private ToolItem toolItem;

	private IPropertyChangeListener preferenceListener = new IPropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent event) {
			if (toolItem != null && !toolItem.isDisposed()
					&& event.getProperty() == IPreferenceConstants.SAVE_AUTOMATICALLY) {
				String autoSaveState = event.getNewValue().toString();
				if (Boolean.valueOf(autoSaveState)) {
					toolItem.setText(IDEWorkbenchMessages.AutoSaveStatusBarItem_on);
				} else {
					toolItem.setText(IDEWorkbenchMessages.AutoSaveStatusBarItem_off);
				}
				toolBar.pack();
			}
		}
	};

	public AutoSaveStatusBarItem() {
	}

	public AutoSaveStatusBarItem(String id) {
		super(id);
	}

	@Override
	protected Control createControl(Composite parent) {
		toolBar = new ToolBar(parent, SWT.HORIZONTAL);
		toolItem = new ToolItem(toolBar, SWT.PUSH);
		boolean autoSave = WorkbenchPlugin.getDefault().getPreferenceStore()
				.getBoolean(IPreferenceConstants.SAVE_AUTOMATICALLY);
		if (autoSave) {
			toolItem.setText(IDEWorkbenchMessages.AutoSaveStatusBarItem_on);
		} else {
			toolItem.setText(IDEWorkbenchMessages.AutoSaveStatusBarItem_off);
		}
		toolItem.setToolTipText(IDEWorkbenchMessages.AutoSaveStatusBarItem_tooltip);
		toolBar.addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(MouseEvent e) {
			}

			@Override
			public void mouseDown(MouseEvent e) {
			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				IPreferenceStore store = WorkbenchPlugin.getDefault().getPreferenceStore();
				boolean autoSaveState = store.getBoolean(IPreferenceConstants.SAVE_AUTOMATICALLY);
				if (autoSaveState) {
					store.setValue(IPreferenceConstants.SAVE_AUTOMATICALLY, false);
				} else {
					store.setValue(IPreferenceConstants.SAVE_AUTOMATICALLY, true);
				}

			}
		});

		WorkbenchPlugin.getDefault().getPreferenceStore().addPropertyChangeListener(preferenceListener);
		toolBar.pack();
		return toolBar;
	}

	@Override
	public void dispose() {
		WorkbenchPlugin.getDefault().getPreferenceStore().removePropertyChangeListener(preferenceListener);
		toolItem.dispose();
		toolBar.dispose();
		super.dispose();
	}
}
