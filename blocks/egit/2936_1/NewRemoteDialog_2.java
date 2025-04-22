package org.eclipse.egit.core;

import org.eclipse.team.core.ProjectSetCapability;
import org.eclipse.team.core.RepositoryProviderType;

public final class GitProviderType extends RepositoryProviderType {
	@Override
	public ProjectSetCapability getProjectSetCapability() {
		return new GitProjectSetCapability();
	}
}
