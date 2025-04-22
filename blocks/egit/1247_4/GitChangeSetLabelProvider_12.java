package org.eclipse.egit.ui.internal.synchronize.mapping;

import org.eclipse.core.resources.mapping.ResourceMapping;
import org.eclipse.core.resources.mapping.ResourceMappingContext;
import org.eclipse.core.resources.mapping.ResourceTraversal;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.synchronize.GitSubscriberMergeContext;
import org.eclipse.egit.core.synchronize.GitSubscriberResourceMappingContext;
import org.eclipse.egit.ui.internal.synchronize.GitChangeSetModelProvider;
import org.eclipse.egit.ui.internal.synchronize.model.GitModelRoot;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.team.core.mapping.ISynchronizationContext;
import org.eclipse.team.ui.mapping.SynchronizationContentProvider;
import org.eclipse.ui.model.WorkbenchContentProvider;

public class GitChangeSetContentProvider extends SynchronizationContentProvider {

	private ITreeContentProvider provider;

	private GitModelRoot modelRoot;

	@Override
	protected ITreeContentProvider getDelegateContentProvider() {
		if (provider == null)
			provider = new WorkbenchContentProvider();

		return provider;
	}

	@Override
	protected String getModelProviderId() {
		return GitChangeSetModelProvider.ID;
	}

	@Override
	protected Object getModelRoot() {
		if (modelRoot == null) {
			GitSubscriberMergeContext context = (GitSubscriberMergeContext) getContext();
			modelRoot = new GitModelRoot(context.getSyncData());
		}

		return modelRoot;
	}

	@Override
	protected ResourceTraversal[] getTraversals(
			ISynchronizationContext context, Object object) {
		if (object instanceof IAdaptable) {
			ResourceMapping rm = getResourceMapping(object);
			GitSubscriberMergeContext ctx = (GitSubscriberMergeContext) getContext();
			ResourceMappingContext rmCtx = new GitSubscriberResourceMappingContext(
					ctx.getSyncData());
			try {
				return rm.getTraversals(rmCtx, new NullProgressMonitor());
			} catch (CoreException e) {
				Activator.logError(e.getMessage(), e);
			}
		}
		return null;
	}

	private ResourceMapping getResourceMapping(Object object) {
		return (ResourceMapping) ((IAdaptable) object)
				.getAdapter(ResourceMapping.class);
	}

	@Override
	public void dispose() {
		if (provider != null)
			provider.dispose();

		super.dispose();
	}

}
