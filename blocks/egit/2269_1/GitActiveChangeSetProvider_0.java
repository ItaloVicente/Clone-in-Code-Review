
package org.eclipse.egit.internal.mylyn.ui.changesets;

import org.eclipse.egit.core.synchronize.GitResourceVariantTreeSubscriber;
import org.eclipse.egit.core.synchronize.dto.GitSynchronizeDataSet;
import org.eclipse.mylyn.internal.team.ui.ContextChangeSet;
import org.eclipse.mylyn.tasks.core.ITask;
import org.eclipse.mylyn.team.ui.AbstractActiveChangeSetProvider;
import org.eclipse.mylyn.team.ui.IContextChangeSet;
import org.eclipse.team.core.subscribers.Subscriber;
import org.eclipse.team.internal.core.subscribers.ActiveChangeSet;
import org.eclipse.team.internal.core.subscribers.ActiveChangeSetManager;
import org.eclipse.team.internal.core.subscribers.SubscriberChangeSetManager;

public class GitActiveChangeSetProvider extends AbstractActiveChangeSetProvider {

	private GitResourceVariantTreeSubscriber subscriberInstance;
	private ActiveChangeSetManager changeSetManager;
	
	@Override
	public ActiveChangeSetManager getActiveChangeSetManager() {
		if (changeSetManager == null) {
			changeSetManager = new SubscriberChangeSetManager(getSubscriber()) {

				protected ActiveChangeSet doCreateSet(String name) {
					return new ActiveChangeSet(this, name);
				}

			};
		}
		return changeSetManager;
	}

	@Override
	public IContextChangeSet createChangeSet(ITask task) {
		return new ContextChangeSet(task, changeSetManager);
	}
	
	private Subscriber getSubscriber() {
		if (subscriberInstance == null) {
			subscriberInstance = new GitResourceVariantTreeSubscriber(
					new GitSynchronizeDataSet());
		}
		return subscriberInstance;
	}

}
