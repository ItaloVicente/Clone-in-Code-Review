/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.examples.readmetool;

import org.eclipse.jface.action.ControlContribution;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPropertyListener;

/**
 * This class demonstrates the contribution of a custom control to the
 * status line for the readme editor.  The control shows the
 * dirty status of the editor.
 */
public class DirtyStateContribution extends ControlContribution implements
        IPropertyListener {
    private Composite composite;

    private Label label;

    private IEditorPart activeEditor;

    /**
     * Creates a new DirtyStateContribution.
     */
    protected DirtyStateContribution() {
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

    /**
     * Called when an editor is activated.
     *
     * @see ReadmeEditorActionBarContributor#setActiveEditor(IEditorPart)
     */
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

    /**
     * Updates the state of the label.
     */
    private void updateState() {
        if (label == null || label.isDisposed())
            return;

        boolean saveNeeded = false;
        if (activeEditor != null)
            saveNeeded = activeEditor.isDirty();
        if (saveNeeded)
        else
    }
}
