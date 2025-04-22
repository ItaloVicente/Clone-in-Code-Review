package org.eclipse.ui.ide;

import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorRegistry;

public interface IUnknownEditorStrategy {

	IEditorDescriptor getEditorDescriptor(String fileName, IEditorRegistry editorRegistry);

}
