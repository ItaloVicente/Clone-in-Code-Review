package org.eclipse.egit.ui.internal.reflog;

import java.util.Objects;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.egit.ui.internal.reflog.ReflogViewContentProvider.ReflogInput;
import org.eclipse.jgit.lib.CheckoutEntry;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.ReflogEntry;
import org.eclipse.jgit.lib.Repository;

public class ReflogItem implements ReflogEntry, IAdaptable {

	private final ReflogEntry entry;

	private final ReflogInput input;

	ReflogItem(ReflogInput input, ReflogEntry entry) {
		this.entry = entry;
		this.input = input;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(Class adapter) {
		if (adapter.isInstance(this)) {
			return this;
		} else if (Repository.class.equals(adapter)) {
			return input.getRepository();
		}
		return null;
	}

	@Override
	public ObjectId getOldId() {
		return entry.getOldId();
	}

	@Override
	public ObjectId getNewId() {
		return entry.getNewId();
	}

	@Override
	public PersonIdent getWho() {
		return entry.getWho();
	}

	@Override
	public String getComment() {
		return entry.getComment();
	}

	@Override
	public CheckoutEntry parseCheckout() {
		return entry.parseCheckout();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof ReflogItem)) {
			return false;
		}
		ReflogItem other = (ReflogItem) obj;
		return input == other.input
				&& Objects.equals(getNewId(), other.getNewId())
				&& Objects.equals(getOldId(), other.getOldId())
				&& Objects.equals(getWho(), other.getWho())
				&& Objects.equals(getComment(), other.getComment());
	}

	@Override
	public int hashCode() {
		return Objects.hash(input, getNewId(), getOldId(), getWho(),
				getComment());
	}
}
