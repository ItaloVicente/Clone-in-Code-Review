package org.eclipse.jgit.lib;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.PersonIdent;

public interface ReflogEntry {

	public abstract ObjectId getOldId();

	public abstract ObjectId getNewId();

	public abstract PersonIdent getWho();

	public abstract String getComment();

	public abstract CheckoutEntry parseCheckout();

}
