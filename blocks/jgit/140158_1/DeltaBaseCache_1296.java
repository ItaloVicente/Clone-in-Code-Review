
package org.eclipse.jgit.internal.storage.file;

import org.eclipse.jgit.lib.CheckoutEntry;
import org.eclipse.jgit.lib.ReflogEntry;

public class CheckoutEntryImpl implements CheckoutEntry {

	private String from;

	private String to;

	CheckoutEntryImpl(ReflogEntry reflogEntry) {
		String comment = reflogEntry.getComment();
		int p1 = CHECKOUT_MOVING_FROM.length();
		int p2 = comment.indexOf(" to "
		int p3 = comment.length();
		from = comment.substring(p1
		to = comment.substring(p2 + " to ".length()
	}

	@Override
	public String getFromBranch() {
		return from;
	}

	@Override
	public String getToBranch() {
		return to;
	}
}
