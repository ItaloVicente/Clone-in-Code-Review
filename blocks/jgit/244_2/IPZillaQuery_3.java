
package org.eclipse.jgit.iplog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Contributor {
	static final Comparator<Contributor> COMPARATOR = new Comparator<Contributor>() {
		public int compare(Contributor a
			return a.name.compareTo(b.name);
		}
	};

	private final String id;

	private final String name;

	private final List<SingleContribution> contributions = new ArrayList<SingleContribution>();

	Contributor(String id
		this.id = id;
		this.name = name;
	}

	String getID() {
		return id;
	}

	String getName() {
		return name;
	}

	Collection<SingleContribution> getContributions() {
		return Collections.unmodifiableCollection(contributions);
	}

	void add(SingleContribution bug) {
		contributions.add(bug);
	}

	@Override
	public String toString() {
		return "Contributor " + getName();
	}
}
