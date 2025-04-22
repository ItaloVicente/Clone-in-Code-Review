package org.eclipse.ui.examples.readmetool;

import org.eclipse.jface.action.ControlContribution;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPropertyListener;

public class DirtyStateContribution extends ControlContribution implements
        IPropertyListener {
    private Composite composite;

    private Label label;

    private IEditorPart activeEditor;

    protected DirtyStateContribution() {
        super("DirtyState"); //$NON-NLS-1$
    }

    @Override
	protected Control createControl(Composite parent) {
        if (composite != null && !composite.isDisposed())
            return composite;

        composite = new Composite(parent, SWT.BORDER);
        composite.setData(this);

        label = new Label(composite, SWT.NONE);
        label.setSize(80, 15);

        updateState();
        return composite;
    }

    public void editorChanged(IEditorPart part) {
        if (activeEditor != null) {
            activeEditor.removePropertyListener(this);
        }
        activeEditor = part;
        if (activeEditor != null) {
            activeEditor.addPropertyListener(this);
        }
        updateState();
    }

    @Override
	public void propertyChanged(Object source, int propID) {
        if (source instanceof IEditorPart)
            updateState();
    }

    private void updateState() {
        if (label == null || label.isDisposed())
            return;

        boolean saveNeeded = false;
        if (activeEditor != null)
            saveNeeded = activeEditor.isDirty();
        if (saveNeeded)
            label.setText(MessageUtil.getString("Save_Needed")); //$NON-NLS-1$
        else
            label.setText(MessageUtil.getString("Clean")); //$NON-NLS-1$
    }
}
