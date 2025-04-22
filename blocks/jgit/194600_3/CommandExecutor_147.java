
package org.eclipse.jgit.internal.diff;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.diff.RenameDetector;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.filter.PathFilter;

public class FilteredRenameDetector {

	private final RenameDetector renameDetector;

	public FilteredRenameDetector(Repository repository) {
		this(new RenameDetector(repository));
	}

	public FilteredRenameDetector(RenameDetector renameDetector) {
		this.renameDetector = renameDetector;
	}

	public List<DiffEntry> compute(List<DiffEntry> diffs
			PathFilter pathFilter) throws IOException {
		return compute(diffs
	}

	public List<DiffEntry> compute(List<DiffEntry> changes
			List<PathFilter> pathFilters) throws IOException {

		if (pathFilters == null) {
		}

		Set<String> paths = new HashSet<>(pathFilters.size());
		for (PathFilter pathFilter : pathFilters) {
			paths.add(pathFilter.getPath());
		}

		List<DiffEntry> filtered = new ArrayList<>();

		for (DiffEntry diff : changes) {
			ChangeType changeType = diff.getChangeType();
			if (changeType != ChangeType.ADD
					|| paths.contains(diff.getNewPath())) {
				filtered.add(diff);
			}
		}

		renameDetector.reset();
		renameDetector.addAll(filtered);
		List<DiffEntry> sourceChanges = renameDetector.compute();

		filtered.clear();

		for (DiffEntry diff : changes) {
			ChangeType changeType = diff.getChangeType();
			if (changeType != ChangeType.DELETE
					|| paths.contains(diff.getOldPath())) {
				filtered.add(diff);
			}
		}

		renameDetector.reset();
		renameDetector.addAll(filtered);
		List<DiffEntry> targetChanges = renameDetector.compute();

		List<DiffEntry> result = new ArrayList<>();

		for (DiffEntry sourceChange : sourceChanges) {
			if (paths.contains(sourceChange.getNewPath())) {
				result.add(sourceChange);
			}
		}
		for (DiffEntry targetChange : targetChanges) {
			if (paths.contains(targetChange.getOldPath())) {
				result.add(targetChange);
			}
		}

		renameDetector.reset();
		return result;
	}
}
