package org.eclipse.egit.ui.internal.commit;

import java.text.MessageFormat;
import java.util.Objects;

import org.eclipse.core.runtime.Assert;
import org.eclipse.egit.core.internal.IRepositoryCommit;
import org.eclipse.egit.core.internal.Utils;
import org.eclipse.egit.ui.internal.GitLabels;
import org.eclipse.egit.ui.internal.UIIcons;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class DiffEditorInput implements IEditorInput {

	private IDocument document;

	private String title;

	private String tooltip;

	private final @NonNull IRepositoryCommit tip;

	private final IRepositoryCommit base;

	public DiffEditorInput(@NonNull IRepositoryCommit commit) {
		this(commit, null, null, null);
	}

	public DiffEditorInput(@NonNull IRepositoryCommit tip,
			IRepositoryCommit base) {
		this(tip, base, null, null);
	}

	public DiffEditorInput(@NonNull IRepositoryCommit tip,
			IRepositoryCommit base, DiffDocument diff) {
		this(tip, base, diff, null);
	}

	public DiffEditorInput(@NonNull IRepositoryCommit tip,
			IRepositoryCommit base, DiffDocument diff, String title) {
		Assert.isLegal(base == null || tip.getRepository().getDirectory()
				.equals(base.getRepository().getDirectory()));
		this.tip = tip;
		this.base = base;
		this.document = diff;
		this.title = title;
	}

	@NonNull
	public IRepositoryCommit getTip() {
		return tip;
	}

	public IRepositoryCommit getBase() {
		return base;
	}

	public void setDocument(IDocument diff) {
		document = diff;
	}

	public IDocument getDocument() {
		return document;
	}

	@Override
	public String getName() {
		if (title == null) {
			if (base == null) {
				title = MessageFormat.format(UIText.DiffEditorInput_Title1,
						Utils.getShortObjectId(tip.getObjectId()),
						GitLabels.getPlainShortLabel(tip.getRepository()));
			} else {
				title = MessageFormat.format(UIText.DiffEditorInput_Title2,
						Utils.getShortObjectId(base.getObjectId()),
						Utils.getShortObjectId(tip.getObjectId()),
						GitLabels.getPlainShortLabel(tip.getRepository()));
			}
		}
		return title;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof DiffEditorInput)) {
			return false;
		}
		DiffEditorInput other = (DiffEditorInput) obj;
		if (!Objects.equals(tip.getRepository().getDirectory(),
				other.tip.getRepository().getDirectory())) {
			return false;
		}
		if (!Objects.equals(tip.getObjectId(), other.tip.getObjectId())) {
			return false;
		}
		if (base == null) {
			if (other.base != null) {
				return false;
			}
		} else {
			if (other.base == null || !Objects.equals(base.getObjectId(),
					other.base.getObjectId())) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(tip.getObjectId(),
				tip.getRepository().getDirectory(),
				base == null ? null : base.getObjectId());
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		if (adapter == IRepositoryCommit.class) {
			return adapter.cast(tip);
		} else if (adapter == RevCommit.class) {
			return adapter.cast(tip.getRevCommit());
		} else if (adapter == Repository.class) {
			return adapter.cast(tip.getRepository());
		}
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
		if (tooltip == null) {
			if (base == null) {
				tooltip = MessageFormat.format(UIText.DiffEditorInput_Tooltip1,
						Utils.getShortObjectId(tip.getObjectId()),
						GitLabels.getPlainShortLabel(tip.getRepository()));
			} else {
				tooltip = MessageFormat.format(
						UIText.DiffEditorInput_Tooltip2,
						Utils.getShortObjectId(base.getObjectId()),
						Utils.getShortObjectId(tip.getObjectId()),
						GitLabels.getPlainShortLabel(tip.getRepository()));
			}
		}
		return tooltip;
	}
}
