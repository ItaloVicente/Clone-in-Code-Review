
package org.eclipse.ui.internal.model;

import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.model.ContributionComparator;
import org.eclipse.ui.model.IContributionService;

public class ContributionService implements IContributionService {

	private WorkbenchAdvisor advisor;

	public ContributionService(WorkbenchAdvisor advisor) {
		this.advisor = advisor;
	}
	
	@Override
	public ContributionComparator getComparatorFor(String contributionType) {
		return advisor.getComparatorFor(contributionType);
	}
}
