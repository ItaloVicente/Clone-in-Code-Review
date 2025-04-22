
package org.eclipse.jgit.lib;

import static org.eclipse.jgit.util.StringUtils.compareIgnoreCase;
import static org.eclipse.jgit.util.StringUtils.compareWithCase;
import static org.eclipse.jgit.util.StringUtils.toLowerCase;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.jgit.util.StringUtils;

class ConfigSnapshot {
	final List<ConfigLine> entryList;
	final Map<Object
	final ConfigSnapshot baseState;
	volatile List<ConfigLine> sorted;
	volatile SectionNames names;

	ConfigSnapshot(List<ConfigLine> entries
		entryList = entries;
		cache = new ConcurrentHashMap<>(16
		baseState = base;
	}

	Set<String> getSections() {
		return names().sections;
	}

	Set<String> getSubsections(String section) {
		Map<String
		Set<String> r = m.get(section);
		if (r == null)
			r = m.get(toLowerCase(section));
		if (r == null)
			return Collections.emptySet();
		return Collections.unmodifiableSet(r);
	}

	Set<String> getNames(String section
		return getNames(section
	}

	Set<String> getNames(String section
		Map<String
		return new CaseFoldingSet(m);
	}

	private Map<String
			String subsection
		List<ConfigLine> s = sorted();
		int idx = find(s
		if (idx < 0)
			idx = -(idx + 1);

		Map<String
		while (idx < s.size()) {
			ConfigLine e = s.get(idx++);
			if (!e.match(section
				break;
			if (e.name == null)
				continue;
			String l = toLowerCase(e.name);
			if (!m.containsKey(l))
				m.put(l
		}
		if (recursive && baseState != null)
			m.putAll(baseState.getNamesInternal(section
		return m;
	}

	String[] get(String section
		List<ConfigLine> s = sorted();
		int idx = find(s
		if (idx < 0)
			return null;
		int end = end(s
		String[] r = new String[end - idx];
		for (int i = 0; idx < end;)
			r[i++] = s.get(idx++).value;
		return r;
	}

	private int find(List<ConfigLine> s
		int low = 0;
		int high = s.size();
		while (low < high) {
			int mid = (low + high) >>> 1;
			ConfigLine e = s.get(mid);
			int cmp = compare2(
					s1
					e.section
			if (cmp < 0)
				high = mid;
			else if (cmp == 0)
				return first(s
			else
				low = mid + 1;
		}
		return -(low + 1);
	}

	private int first(List<ConfigLine> s
		while (0 < i) {
			if (s.get(i - 1).match(s1
				i--;
			else
				return i;
		}
		return i;
	}

	private int end(List<ConfigLine> s
		while (i < s.size()) {
			if (s.get(i).match(s1
				i++;
			else
				return i;
		}
		return i;
	}

	private List<ConfigLine> sorted() {
		List<ConfigLine> r = sorted;
		if (r == null)
			sorted = r = sort(entryList);
		return r;
	}

	private static List<ConfigLine> sort(List<ConfigLine> in) {
		List<ConfigLine> sorted = new ArrayList<>(in.size());
		for (ConfigLine line : in) {
			if (line.section != null && line.name != null)
				sorted.add(line);
		}
		Collections.sort(sorted
		return sorted;
	}

	private static int compare2(
			String aSection
			String bSection
		int c = compareIgnoreCase(aSection
		if (c != 0)
			return c;

		if (aSubsection == null && bSubsection != null)
			return -1;
		if (aSubsection != null && bSubsection == null)
			return 1;
		if (aSubsection != null) {
			c = compareWithCase(aSubsection
			if (c != 0)
				return c;
		}

		return compareIgnoreCase(aName
	}

	private static class LineComparator implements Comparator<ConfigLine> {
		@Override
		public int compare(ConfigLine a
			return compare2(
					a.section
					b.section
		}
	}

	private SectionNames names() {
		SectionNames n = names;
		if (n == null)
			names = n = new SectionNames(this);
		return n;
	}

	private static class SectionNames {
		final CaseFoldingSet sections;
		final Map<String

		SectionNames(ConfigSnapshot cfg) {
			Map<String
			Map<String
			while (cfg != null) {
				for (ConfigLine e : cfg.entryList) {
					if (e.section == null)
						continue;

					String l1 = toLowerCase(e.section);
					if (!sec.containsKey(l1))
						sec.put(l1

					if (e.subsection == null)
						continue;

					Set<String> m = sub.get(l1);
					if (m == null) {
						m = new LinkedHashSet<>();
						sub.put(l1
					}
					m.add(e.subsection);
				}
				cfg = cfg.baseState;
			}

			sections = new CaseFoldingSet(sec);
			subsections = sub;
		}
	}

	private static class CaseFoldingSet extends AbstractSet<String> {
		private final Map<String

		CaseFoldingSet(Map<String
			this.names = names;
		}

		@Override
		public boolean contains(Object needle) {
			if (needle instanceof String) {
				String n = (String) needle;
				return names.containsKey(n)
						|| names.containsKey(StringUtils.toLowerCase(n));
			}
			return false;
		}

		@Override
		public Iterator<String> iterator() {
			final Iterator<String> i = names.values().iterator();
			return new Iterator<String>() {
				@Override
				public boolean hasNext() {
					return i.hasNext();
				}

				@Override
				public String next() {
					return i.next();
				}

				@Override
				public void remove() {
					throw new UnsupportedOperationException();
				}
			};
		}

		@Override
		public int size() {
			return names.size();
		}
	}
}
