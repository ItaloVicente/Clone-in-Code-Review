package org.eclipse.egit.core.synchronize;

import org.eclipse.egit.core.synchronize.dto.GitSynchronizeDataSet;
import org.eclipse.team.core.subscribers.SubscriberResourceMappingContext;

public class GitSubscriberResourceMappingContext extends
		SubscriberResourceMappingContext {

	private final GitSynchronizeDataSet data;

	public GitSubscriberResourceMappingContext(GitSynchronizeDataSet data) {
		super(new GitResourceVariantTreeSubscriber(data), true);
		this.data = data;
	}

	public GitSynchronizeDataSet getSyncData() {
		return data;
	}

}
