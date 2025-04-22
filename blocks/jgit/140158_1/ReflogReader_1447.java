package org.eclipse.jgit.lib;

public interface ReflogEntry {




	ObjectId getOldId();

	ObjectId getNewId();

	PersonIdent getWho();

	String getComment();

	CheckoutEntry parseCheckout();

}
