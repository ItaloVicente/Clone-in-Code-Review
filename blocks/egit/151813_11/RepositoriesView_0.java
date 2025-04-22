package org.eclipse.egit.ui.internal.components;

import org.eclipse.egit.core.internal.Utils;
import org.eclipse.egit.ui.Activator;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.window.DefaultToolTip;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class MessagePopupTextCellEditor extends TextCellEditor {

	private static final int DEFAULT_DELAY_MILLIS = 200;

	private static final RGB DEFAULT_BACKGROUND = new RGB(0xFF, 0x96, 0x96);

	private DefaultToolTip errorPopup;

	public MessagePopupTextCellEditor(Composite parent) {
		super(parent);
	}

	public MessagePopupTextCellEditor(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	protected boolean dependsOnExternalFocusListener() {
		return false;
	}

	@Override
	protected void focusLost() {
		if (isActivated()) {
			fireCancelEditor();
		}
	}

	@Override
	protected Control createControl(Composite parent) {
		Control control = super.createControl(parent);
		errorPopup = new DefaultToolTip(control, ToolTip.NO_RECREATE, true);
		errorPopup.setPopupDelay(DEFAULT_DELAY_MILLIS);
		errorPopup.setBackgroundColor(Activator.getDefault()
				.getResourceManager().createColor(DEFAULT_BACKGROUND));
		control.addDisposeListener(event -> errorPopup.hide());
		addListener(new ICellEditorListener() {

			@Override
			public void editorValueChanged(boolean oldValidState,
					boolean newValidState) {
				if (newValidState) {
					errorPopup.hide();
					return;
				}
				Control editor = getControl();
				Point pos = editor.getSize();
				errorPopup.setText(getErrorMessage());
				pos.x = 0;
				errorPopup.show(pos);
			}

			@Override
			public void cancelEditor() {
				errorPopup.hide();
			}

			@Override
			public void applyEditorValue() {
				errorPopup.hide();
			}
		});
		if ((text.getStyle() & SWT.SINGLE) != 0) {
			text.addVerifyListener(
					event -> event.text = Utils.firstLine(event.text));
		}
		return control;
	}

	public DefaultToolTip getToolTip() {
		return errorPopup;
	}
}
