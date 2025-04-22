package org.eclipse.egit.core.internal.util;

import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.egit.core.GitCorePreferences;
import org.eclipse.egit.core.internal.merge.StrategyRecursiveModel;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.jgit.merge.StrategyRecursive;
import org.eclipse.jgit.merge.ThreeWayMergeStrategy;

public class OperationUtil {

	private static final StrategyRecursiveModel LOGICAL_MODEL_RECURSIVE_STRATEGY = new StrategyRecursiveModel();

	private static final ThreeWayMergeStrategy PLAIN_RECURSIVE_STRATEGY = MergeStrategy.RECURSIVE;

	public static boolean isUseLogicalModel() {
		String corePluginId = org.eclipse.egit.core.Activator.getPluginId();
		IEclipsePreferences d = DefaultScope.INSTANCE.getNode(corePluginId);
		IEclipsePreferences p = InstanceScope.INSTANCE.getNode(corePluginId);
		return p.getBoolean(GitCorePreferences.core_useLogicalModel,
				d.getBoolean(GitCorePreferences.core_useLogicalModel, true));
	}

	public static MergeStrategy getMergeStrategy() {
		if (isUseLogicalModel()) {
			return LOGICAL_MODEL_RECURSIVE_STRATEGY;
		} else {
			return PLAIN_RECURSIVE_STRATEGY;
		}

	}

}
