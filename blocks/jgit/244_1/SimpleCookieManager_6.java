
package org.eclipse.jgit.iplog;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

class Project {
	static final Comparator<Project> COMPARATOR = new Comparator<Project>() {
		public int compare(Project a
			return a.getID().compareTo(b.getID());
		}
	};

	private final String id;

	private final String name;

	private String comments;

	private final Set<String> licenses = new TreeSet<String>();

	private String version;

	Project(String id
		this.id = id;
		this.name = name;
	}

	String getID() {
		return id;
	}

	String getName() {
		return name;
	}

	String getComments() {
		return comments;
	}

	void setComments(String comments) {
		this.comments = comments;
	}

	Set<String> getLicenses() {
		return Collections.unmodifiableSet(licenses);
	}

	void addLicense(String licenseName) {
		licenses.add(licenseName);
	}

	String getVersion() {
		return version;
	}

	void setVersion(String v) {
		version = v;
	}

	@Override
	public String toString() {
		return "Project " + getID() + " (" + getName() + ")";
	}
}
