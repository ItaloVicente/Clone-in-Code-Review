
package org.eclipse.jgit.lib;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RefComparator implements Comparator<Ref> {

	public static final RefComparator INSTANCE = new RefComparator();

	@Override
	public int compare(Ref o1
		return compareTo(o1
	}

	public static Collection<Ref> sort(Collection<Ref> refs) {
		final List<Ref> r = new ArrayList<>(refs);
		Collections.sort(r
		return r;
	}

	public static int compareTo(Ref o1
		return o1.getName().compareTo(o2);
	}

	public static int compareTo(Ref o1
		return o1.getName().compareTo(o2.getName());
	}
}
