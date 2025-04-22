
package org.eclipse.ui;

public interface IEditorMatchingStrategy {
    
    boolean matches(IEditorReference editorRef, IEditorInput input);
    
}
