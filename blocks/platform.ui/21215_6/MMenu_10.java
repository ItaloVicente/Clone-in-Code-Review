package org.eclipse.e4.ui.model.application.impl;

import org.eclipse.e4.ui.model.application.MLifecycleContribution;

import org.eclipse.emf.ecore.EClass;

public class LifecycleContributionImpl extends ContributionImpl implements MLifecycleContribution {
	protected LifecycleContributionImpl() {
		super();
	}

	@Override
	protected EClass eStaticClass() {
		return ApplicationPackageImpl.Literals.LIFECYCLE_CONTRIBUTION;
	}

} //LifecycleContributionImpl
