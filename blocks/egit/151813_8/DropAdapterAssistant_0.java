package org.eclipse.egit.ui.internal.components;

import org.eclipse.egit.ui.Activator;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.window.DefaultToolTip;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class MessagePopupTextCellEditor extends TextCellEditor {

	private static final int DEFAULT_DELAY_MILLIS = 200;

	private static final RGB DEFAULT_BACKGROUND = new RGB(0xFF, 0x96, 0x96);

	private DefaultToolTip errorMessage;

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
		errorMessage = new DefaultToolTip(control, ToolTip.NO_RECREATE, true);
		errorMessage.setPopupDelay(DEFAULT_DELAY_MILLIS);
		errorMessage.setBackgroundColor(Activator.getDefault()
				.getResourceManager().createColor(DEFAULT_BACKGROUND));
		control.addDisposeListener(event -> errorMessage.hide());
		addListener(new ICellEditorListener() {

			@Override
			public void editorValueChanged(boolean oldValidState,
					boolean newValidState) {
				if (newValidState) {
					errorMessage.hide();
					return;
				}
				Control editor = getControl();
				Point pos = editor.getSize();
				errorMessage.setText(getErrorMessage());
				pos.x = 0;
				errorMessage.show(pos);
			}

			@Override
			public void cancelEditor() {
				errorMessage.hide();
			}

			@Override
			public void applyEditorValue() {
				errorMessage.hide();
			}
		});
		return control;
	}

	public DefaultToolTip getToolTip() {
		return errorMessage;
	}
}
