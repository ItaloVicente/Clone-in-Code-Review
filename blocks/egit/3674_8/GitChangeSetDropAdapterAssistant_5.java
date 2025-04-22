package org.eclipse.egit.ui.internal.synchronize;

import java.lang.reflect.Method;

import org.eclipse.compare.CompareNavigator;
import org.eclipse.compare.INavigatable;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.egit.core.project.GitProjectData;
import org.eclipse.egit.ui.JobFamilies;

class GitTreeCompareNavigator extends CompareNavigator {

	private static final Class<CompareNavigator> COMPARE_NAVIGATOR_CLASS = CompareNavigator.class;

	private final CompareNavigator mainNavigator;

	public GitTreeCompareNavigator(CompareNavigator mainNavigator) {
		this.mainNavigator = mainNavigator;
	}

	@Override
	protected INavigatable[] getNavigatables() {
		try {
			Method baseNavigables = COMPARE_NAVIGATOR_CLASS.getDeclaredMethod(
					"getNavigatables", Void.class); //$NON-NLS-1$
			baseNavigables.setAccessible(true);

			return (INavigatable[]) baseNavigables.invoke(mainNavigator,
					Void.class);
		} catch (Exception e) {
		}

		return new INavigatable[0];
	}

	@Override
	public boolean selectChange(boolean next) {
		IJobManager manager = Job.getJobManager();
		try {
			manager.join(JobFamilies.ADD_TO_INDEX, null);
			manager.join(JobFamilies.REMOVE_FROM_INDEX, null);
			manager.join(GitProjectData.REPOSITORIES_CHANGE_JOB, null);
		} catch (InterruptedException e) {
		}

		return mainNavigator.selectChange(next);
	}

}
