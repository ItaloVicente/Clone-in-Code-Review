package org.eclipse.egit.ui.internal.commit;

import org.eclipse.egit.ui.UIText;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;

public class NotesEditorPage extends FormPage {

	public NotesEditorPage(FormEditor editor, String id, String title) {
		super(editor, id, title);
	}

	public NotesEditorPage(FormEditor editor) {
		this(editor, "notePage", UIText.NotesEditorPage_Title); //$NON-NLS-1$
	}

	protected RepositoryCommit getCommit() {
		return (RepositoryCommit) getEditor()
				.getAdapter(RepositoryCommit.class);
	}

	@Override
	protected void createFormContent(IManagedForm managedForm) {
		Composite body = managedForm.getForm().getBody();
		GridLayoutFactory.swtDefaults().numColumns(1).applyTo(body);

		NotesBlock block = new NotesBlock(getCommit());
		block.createContent(managedForm, body);
		block.selectFirstNote();
	}
}
