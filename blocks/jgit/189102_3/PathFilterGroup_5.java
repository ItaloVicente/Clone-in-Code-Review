
package org.eclipse.jgit.treewalk.filter;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jgit.lib.BloomFilter;
import org.eclipse.jgit.lib.BloomFilter.Key;
import org.eclipse.jgit.lib.CommitGraph;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup.Group;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup.Single;
import org.eclipse.jgit.util.RawParseUtils;

public class BloomKeyParser {

	public static BloomFilter.Key[] parse(TreeFilter filter
			CommitGraph commitGraph) {
		if (!(filter instanceof AndTreeFilter)) {
			return null;
		}
		TreeFilter[] filters = ((AndTreeFilter) filter).getTreeFilters();
		if (filters == null || filters.length != 2) {
			return null;
		}

		boolean diff = false;
		List<String> paths = new ArrayList<>();

		for (TreeFilter tf : filters) {
			if (tf == TreeFilter.ANY_DIFF) {
				diff = true;
			}
			if (tf instanceof PathFilter) {
				String path = ((PathFilter) tf).getPath();
				paths.add(path);
			}
			if (tf instanceof Single) {
				String path = ((Single) tf).getPath();
				paths.add(path);
			}
			if (tf instanceof Group) {
				byte[][] pathsByte = ((Group) tf).getFullpaths().toArray();
				for (int i = 0; i < pathsByte.length; i ++){
					paths.add(RawParseUtils.decode(pathsByte[i]));
				}
			}
		}

		if (!diff || paths.size() <= 0) {
			return null;
		}

		BloomFilter.Key[] bloomKeys = new Key[paths.size()];
		for (int i = 0; i < paths.size(); i++) {
			BloomFilter.Key key = commitGraph.newBloomKey(paths.get(i));
			if (key == null) {
				return null;
			}
			bloomKeys[i] = key;
		}
		return bloomKeys;
	}
}
