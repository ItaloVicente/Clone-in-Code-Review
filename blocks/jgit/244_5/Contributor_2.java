
package org.eclipse.jgit.iplog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Committer {
	static final Comparator<Committer> COMPARATOR = new Comparator<Committer>() {
		public int compare(Committer a
			int cmp = a.firstName.compareTo(b.firstName);
			if (cmp == 0)
				cmp = a.lastName.compareTo(b.lastName);
			return cmp;
		}
	};

	private final String id;

	private String firstName;

	private String lastName;

	private String affiliation;

	private boolean hasCommits;

	private String comments;

	private final Set<String> emailAddresses = new HashSet<String>();

	private final List<ActiveRange> active = new ArrayList<ActiveRange>(2);

	Committer(String id) {
		this.id = id;
	}

	String getID() {
		return id;
	}

	String getFirstName() {
		return firstName;
	}

	void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	String getLastName() {
		return lastName;
	}

	void setLastName(String lastName) {
		this.lastName = lastName;
	}

	String getAffiliation() {
		return affiliation;
	}

	void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

	boolean isActive() {
		if (active.isEmpty())
			return false;
		ActiveRange last = active.get(active.size() - 1);
		return last.end == null;
	}

	boolean hasCommits() {
		return hasCommits;
	}

	void setHasCommits(boolean hasCommits) {
		this.hasCommits = hasCommits;
	}

	String getComments() {
		return comments;
	}

	void setComments(String comments) {
		this.comments = comments;
	}

	void addEmailAddress(String email) {
		emailAddresses.add(email);
	}

	void addActiveRange(ActiveRange r) {
		active.add(r);
		Collections.sort(active
			public int compare(ActiveRange a
				return a.begin.compareTo(b.begin);
			}
		});
	}

	boolean inRange(Date when) {
		for (ActiveRange ar : active) {
			if (ar.contains(when))
				return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Committer " + getFirstName() + " " + getLastName();
	}

	static class ActiveRange {
		private final Date begin;

		private final Date end;

		ActiveRange(Date begin
			this.begin = begin;
			this.end = end;
		}

		boolean contains(Date when) {
			if (when.compareTo(begin) < 0)
				return false;
			if (end == null)
				return true;
			return when.compareTo(end) < 0;
		}
	}
}
