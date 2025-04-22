
package org.eclipse.jgit.storage.file;

public class CheckoutEntry {
	static final String CHECKOUT_MOVING_FROM = "checkout: moving from ";

	private String from;

	private String to;

	CheckoutEntry(ReflogEntry reflogEntry) {
		String comment = reflogEntry.getComment();
		int p1 = CHECKOUT_MOVING_FROM.length();
		int p2 = comment.indexOf(" to "
		int p3 = comment.length();
		from = comment.substring(p1
		to = comment.substring(p2 + " to ".length()
	}

	public String getFromBranch() {
		return from;
	}

	public String getToBranch() {
		return to;
	}
}
