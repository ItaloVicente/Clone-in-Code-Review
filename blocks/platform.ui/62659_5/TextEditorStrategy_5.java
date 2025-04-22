package org.eclipse.ui.internal.ide;

import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.ide.IUnknownEditorStrategy;

public final class TextEditorStrategy implements IUnknownEditorStrategy {

	@Override
	public IEditorDescriptor getEditorDescriptor(String name, IEditorRegistry editorReg) {
		return editorReg.findEditor(IDEWorkbenchPlugin.DEFAULT_TEXT_EDITOR_ID);
	}
}
