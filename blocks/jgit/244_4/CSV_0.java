
package org.eclipse.jgit.iplog;

import java.util.Comparator;

class CQ {
	static final Comparator<CQ> COMPARATOR = new Comparator<CQ>() {
		public int compare(CQ a
			int cmp = state(a) - state(b);
			if (cmp == 0)
				cmp = compare(a.getID()
			return cmp;
		}

		private int state(CQ a) {
			if ("approved".equals(a.getState()))
				return 1;
			return 50;
		}

		private int compare(long a
			return a < b ? -1 : a == b ? 0 : 1;
		}
	};

	private final long id;

	private String description;

	private String license;

	private String use;

	private String state;

	private String comments;

	CQ(final long id) {
		this.id = id;
	}

	long getID() {
		return id;
	}

	String getDescription() {
		return description;
	}

	void setDescription(String description) {
		this.description = description;
	}

	String getLicense() {
		return license;
	}

	void setLicense(String license) {
		this.license = license;
	}

	String getUse() {
		return use;
	}

	void setUse(String use) {
		this.use = use;
	}

	String getState() {
		return state;
	}

	void setState(String state) {
		this.state = state;
	}

	String getComments() {
		return comments;
	}

	void setComments(String comments) {
		this.comments = comments;
	}

	@Override
	public int hashCode() {
		return (int) getID();
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof CQ) {
			return ((CQ) other).getID() == getID();
		}
		return false;
	}

	@Override
	public String toString() {
		return "CQ " + getID();
	}
}
