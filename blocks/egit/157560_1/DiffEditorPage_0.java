package org.eclipse.egit.ui.internal.commit;

import java.util.Objects;

import org.eclipse.egit.ui.internal.UIIcons;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class DiffEditorInput implements IEditorInput {

	private final DiffDocument document;

	private final String title;

	public DiffEditorInput(DiffDocument diff) {
		this(diff, UIText.DiffEditorPage_Title);
	}

	public DiffEditorInput(DiffDocument diff, String title) {
		document = diff;
		this.title = title;
	}

	public DiffDocument getDocument() {
		return document;
	}

	@Override
	public String getName() {
		return title;
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof DiffEditorInput)
				&& Objects.equals(document, ((DiffEditorInput) obj).document);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(document);
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		return null;
	}

	@Override
	public boolean exists() {
		return false;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return UIIcons.CHANGESET;
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return null;
	}
}
