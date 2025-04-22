package org.eclipse.ui;

public interface IAutoSaveableEditorPart extends IEditorPart {

	default public boolean getAutoSavePolicy() {
		return true;
	}

}
