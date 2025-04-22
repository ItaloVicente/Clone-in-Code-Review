
package org.eclipse.jgit.merge;

import java.text.MessageFormat;
import java.util.HashMap;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.Repository;

public abstract class MergeStrategy {
	public static final MergeStrategy OURS = new StrategyOneSided("ours"

	public static final MergeStrategy THEIRS = new StrategyOneSided("theirs"

	public static final ThreeWayMergeStrategy SIMPLE_TWO_WAY_IN_CORE = new StrategySimpleTwoWayInCore();

	public static final ThreeWayMergeStrategy RESOLVE = new StrategyResolve();

	public static final ThreeWayMergeStrategy RECURSIVE = new StrategyRecursive();

	private static final HashMap<String

	static {
		register(OURS);
		register(THEIRS);
		register(SIMPLE_TWO_WAY_IN_CORE);
		register(RESOLVE);
		register(RECURSIVE);
	}

	public static void register(MergeStrategy imp) {
		register(imp.getName()
	}

	public static synchronized void register(final String name
			final MergeStrategy imp) {
		if (STRATEGIES.containsKey(name))
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().mergeStrategyAlreadyExistsAsDefault
		STRATEGIES.put(name
	}

	public static synchronized MergeStrategy get(String name) {
		return STRATEGIES.get(name);
	}

	public static synchronized MergeStrategy[] get() {
		final MergeStrategy[] r = new MergeStrategy[STRATEGIES.size()];
		STRATEGIES.values().toArray(r);
		return r;
	}

	public abstract String getName();

	public abstract Merger newMerger(Repository db);

	public abstract Merger newMerger(Repository db

	public abstract Merger newMerger(ObjectInserter inserter
}
