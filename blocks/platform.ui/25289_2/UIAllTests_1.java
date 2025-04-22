
package org.eclipse.e4.ui.internal.workbench;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class TopologicalSort<T, ID> {
	private final Map<ID, Collection<T>> mappedObjects = new HashMap<ID, Collection<T>>();
	private final Map<ID, Collection<ID>> requires = new HashMap<ID, Collection<ID>>();
	private final Map<ID, Collection<ID>> depends = new HashMap<ID, Collection<ID>>();

	protected abstract ID getId(T object);

	protected abstract Collection<ID> getRequirements(ID id);

	protected abstract Collection<ID> getDependencies(ID id);

	public T[] sort(T... objects) {
		if (objects.length <= 1) {
			return objects;
		}

		addAll(objects);
		return process(objects);
	}

	private T[] process(T[] results) {
		buildDependencyGraph();

		int resultsIndex = 0;
		List<ID> sortedByOutdegree = new ArrayList<ID>(requires.keySet());
		Comparator<ID> outdegreeSorter = new Comparator<ID>() {
			@Override
			public int compare(ID o1, ID o2) {
				assert requires.containsKey(o1) && requires.containsKey(o2);
				int comparison = requires.get(o1).size() - requires.get(o2).size();
				if (comparison == 0) {
					return depends.get(o2).size() - depends.get(o1).size();
				}
				return comparison;
			}
		};
		Collections.sort(sortedByOutdegree, outdegreeSorter);

		while (!sortedByOutdegree.isEmpty()) {
			if (!requires.get(sortedByOutdegree.get(0)).isEmpty()) {
				Collections.sort(sortedByOutdegree, outdegreeSorter);
			}
			LinkedList<ID> cycleToBeDone = new LinkedList<ID>();
			cycleToBeDone.add(sortedByOutdegree.remove(0));
			while (!cycleToBeDone.isEmpty()) {
				ID bundleId = cycleToBeDone.removeFirst();
				assert depends.containsKey(bundleId) && requires.containsKey(bundleId);
				for (T ext : mappedObjects.get(bundleId)) {
					results[resultsIndex++] = ext;
				}
				for (ID reqId : requires.get(bundleId)) {
					cycleToBeDone.add(reqId);
					sortedByOutdegree.remove(reqId);
					depends.get(reqId).remove(bundleId);
				}
				requires.remove(bundleId);
				for (ID depId : depends.get(bundleId)) {
					requires.get(depId).remove(bundleId);
				}
				depends.remove(bundleId);
			}
		}
		return results;
	}

	private void addAll(T... objects) {
		for (T o : objects) {
			ID id = getId(o);
			Collection<T> exts = mappedObjects.get(id);
			if (exts == null) {
				mappedObjects.put(id, exts = new HashSet<T>());
			}
			exts.add(o);
		}
	}

	private void buildDependencyGraph() {
		requires.clear();
		depends.clear();
		for (ID id : mappedObjects.keySet()) {
			requires.put(id, new HashSet<ID>());
			depends.put(id, new HashSet<ID>());
		}

		for (ID subjectId : mappedObjects.keySet()) {
			assert requires.containsKey(subjectId) && depends.containsKey(subjectId);

			Collection<ID> requirements = getRequirements(subjectId);
			if (requirements != null) {
				for (ID requiredId : requirements) {
					assert !requiredId.equals(subjectId) : "self-cycles not supported"; //$NON-NLS-1$
					if (!mappedObjects.containsKey(requiredId)) {
						continue;
					}
					depends.get(requiredId).add(subjectId);
					requires.get(subjectId).add(requiredId);
				}
			}

			Collection<ID> dependencies = getDependencies(subjectId);
			if (dependencies != null) {
				for (ID dependentId : dependencies) {
					assert !dependentId.equals(subjectId) : "self-cycles not supported"; //$NON-NLS-1$
					if (!mappedObjects.containsKey(dependentId)) {
						continue;
					}
					requires.get(dependentId).add(subjectId);
					depends.get(subjectId).add(dependentId);
				}
			}
		}
	}
}
