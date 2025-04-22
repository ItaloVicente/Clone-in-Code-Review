package org.eclipse.ui.examples.readmetool;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.MarkerUtilities;
import org.osgi.framework.Bundle;

public class AddReadmeMarkerAction extends Action {
    private ITextEditor textEditor;

    private Object[][] customAttributes;

    private String message;

    private final static String MARKER_TYPE = "org.eclipse.ui.examples.readmetool.readmemarker"; //$NON-NLS-1$

    public AddReadmeMarkerAction(ITextEditor editor, String label,
            Object[][] attributes, String message) {
        textEditor = editor;
        setText(label);
        this.customAttributes = attributes;
        this.message = message;
    }

    public void run() {
        Map attributes = new HashMap(11);

        ITextSelection selection = (ITextSelection) textEditor
                .getSelectionProvider().getSelection();
        if (!selection.isEmpty()) {

            int start = selection.getOffset();
            int length = selection.getLength();

            if (length < 0) {
                length = -length;
                start -= length;
            }

            MarkerUtilities.setCharStart(attributes, start);
            MarkerUtilities.setCharEnd(attributes, start + length);

            int line = selection.getStartLine();
            MarkerUtilities.setLineNumber(attributes, line == -1 ? -1
                    : line + 1);

            for (int i = 0; i < customAttributes.length; i++) {
                attributes.put(customAttributes[i][0], customAttributes[i][1]);
            }

            MarkerUtilities.setMessage(attributes, message);
        }

        try {
            MarkerUtilities
                    .createMarker(getResource(), attributes, MARKER_TYPE);
        } catch (CoreException x) {
            Bundle bundle = ReadmePlugin.getDefault().getBundle();
            Platform.getLog(bundle).log(x.getStatus());

            Shell shell = textEditor.getSite().getShell();
            String title = MessageUtil
                    .getString("Add_readme_marker_error_title"); //$NON-NLS-1$
            String msg = MessageUtil
                    .getString("Add_readme_marker_error_message"); //$NON-NLS-1$

            ErrorDialog.openError(shell, title, msg, x.getStatus());
        }
    }

    protected IResource getResource() {
        IEditorInput input = textEditor.getEditorInput();
        return (IResource) ((IAdaptable) input).getAdapter(IResource.class);
    }
}
