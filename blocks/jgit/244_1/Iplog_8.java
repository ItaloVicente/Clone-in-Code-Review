
package org.eclipse.jgit.iplog;

import java.util.Comparator;
import java.util.Date;

class SingleContribution {
	public static final Comparator<SingleContribution> COMPARATOR = new Comparator<SingleContribution>() {
		public int compare(SingleContribution a
			return a.created.compareTo(b.created);
		}
	};

	private final String id;

	private String summary;

	private Date created;

	private String bugId;

	private String size;

	SingleContribution(String id
		this.id = id;
		this.summary = summary;
		this.created = created;
	}

	String getID() {
		return id;
	}

	Date getCreated() {
		return created;
	}

	String getSummary() {
		return summary;
	}

	String getBugID() {
		return bugId;
	}

	void setBugID(String id) {
		bugId = id;
	}

	String getSize() {
		return size;
	}

	void setSize(String sz) {
		size = sz;
	}
}
