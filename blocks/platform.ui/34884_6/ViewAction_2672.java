
package org.eclipse.ui.internal.menus;

import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.e4.ui.model.application.MApplicationElement;
import org.eclipse.jface.action.ContributionManager;
import org.eclipse.ui.ISourceProvider;
import org.eclipse.ui.menus.AbstractContributionFactory;
import org.eclipse.ui.menus.IMenuService;

public class SlaveMenuService implements IMenuService {
	private IMenuService parentService;

	@Override
	public void addSourceProvider(ISourceProvider provider) {
		parentService.addSourceProvider(provider);
	}

	@Override
	public void removeSourceProvider(ISourceProvider provider) {
		parentService.removeSourceProvider(provider);
	}

	@Override
	public void addContributionFactory(AbstractContributionFactory factory) {
		parentService.addContributionFactory(factory);
	}

	@Override
	public void dispose() {
	}

	@Override
	public void removeContributionFactory(AbstractContributionFactory factory) {
		parentService.removeContributionFactory(factory);
	}

	@Override
	public void populateContributionManager(ContributionManager mgr, String location) {
		populateContributionManager(model, mgr, location);
	}

	public void populateContributionManager(MApplicationElement model, ContributionManager mgr,
			String location) {
		if (parentService instanceof SlaveMenuService) {
			((SlaveMenuService) parentService).populateContributionManager(model, mgr, location);
		} else if (parentService instanceof WorkbenchMenuService) {
			((WorkbenchMenuService) parentService)
					.populateContributionManager(model, mgr, location);
		}
	}
	@Override
	public void releaseContributions(ContributionManager mgr) {
		parentService.releaseContributions(mgr);
	}

	@Override
	public IEvaluationContext getCurrentState() {
		return parentService.getCurrentState();
	}

	public SlaveMenuService(IMenuService parent, MApplicationElement model) {
		parentService = parent;
		this.model = model;
	}

	private MApplicationElement model;

	public MApplicationElement getModel() {
		return model;
	}
}
