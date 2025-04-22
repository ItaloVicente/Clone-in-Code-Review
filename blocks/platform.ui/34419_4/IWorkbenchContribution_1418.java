package org.eclipse.ui.menus;

import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.jface.action.ContributionManager;
import org.eclipse.ui.services.IServiceWithSources;

public interface IMenuService extends IServiceWithSources {

	public void addContributionFactory(AbstractContributionFactory factory);

	public void removeContributionFactory(AbstractContributionFactory factory);

	public void populateContributionManager(ContributionManager mgr,
			String location);

	public void releaseContributions(ContributionManager mgr);

	public IEvaluationContext getCurrentState();
}
