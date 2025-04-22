
package org.eclipse.ui.model;

public interface IContributionService {

	public static final String TYPE_PROPERTY = "property"; //$NON-NLS-1$

	public static final String TYPE_PREFERENCE = "preference"; //$NON-NLS-1$

	public ContributionComparator getComparatorFor(String contributionType);
}
