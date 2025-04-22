package org.eclipse.egit.ui.view.synchronize;

import org.eclipse.ui.IEditorReference;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

class ComapreEditorTitleMatcher extends BaseMatcher<IEditorReference> {

	private final String fileName;

	public ComapreEditorTitleMatcher(String fileName) {
		this.fileName = fileName;
	}

	public void describeTo(Description description) {
		description.appendText("Editor that title contins text: " + fileName);
	}

	public boolean matches(Object item) {
		if (item instanceof IEditorReference) {
			IEditorReference editor = (IEditorReference) item;
			return editor.getTitle().contains(fileName);
		}

		return false;
	}

}
