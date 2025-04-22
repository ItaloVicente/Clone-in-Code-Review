package org.eclipse.egit.ui.internal.repository;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.egit.ui.internal.repository.tree.FilterableNode;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryTreeNodeType;

class FilterCache {

	public static final FilterCache INSTANCE = new FilterCache();

	private final Map<File, Map<RepositoryTreeNodeType, String>> cache = new HashMap<>();

	private FilterCache() {
	}

	public void set(FilterableNode node, String filter) {
		cache.computeIfAbsent(node.getRepository().getDirectory(),
				k -> new HashMap<>()).put(node.getType(), filter);
		node.setFilter(filter);
	}

	public String get(FilterableNode node) {
		Map<RepositoryTreeNodeType, String> filters = cache
				.get(node.getRepository().getDirectory());
		return filters == null ? null : filters.get(node.getType());
	}

	public void clear() {
		cache.clear();
	}

	public void keepOnly(Collection<File> gitDirs) {
		cache.keySet().retainAll(gitDirs);
	}
}
