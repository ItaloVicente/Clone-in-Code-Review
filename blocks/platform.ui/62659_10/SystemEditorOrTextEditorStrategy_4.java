package org.eclipse.ui.internal.ide;

import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.ide.IUnknownEditorStrategy;

public final class SystemEditorOrTextEditorStrategy implements IUnknownEditorStrategy {
	static final String EXTENSION_ID = "org.eclipse.ui.ide.systemEditorThenTextEditor"; //$NON-NLS-1$

	@Override
	public IEditorDescriptor getEditorDescriptor(String name, IEditorRegistry editorReg) {
		IEditorDescriptor editorDesc = null;
		if (editorReg.isSystemInPlaceEditorAvailable(name)) {
			editorDesc = editorReg.findEditor(IEditorRegistry.SYSTEM_INPLACE_EDITOR_ID);
		}

		if (editorDesc == null && editorReg.isSystemExternalEditorAvailable(name)) {
			editorDesc = editorReg.findEditor(IEditorRegistry.SYSTEM_EXTERNAL_EDITOR_ID);
		}

		if (editorDesc == null) {
			editorDesc = editorReg.findEditor(IDEWorkbenchPlugin.DEFAULT_TEXT_EDITOR_ID);
		}
		return editorDesc;
	}
}
