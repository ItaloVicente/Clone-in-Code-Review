package org.eclipse.egit.ui.internal.synchronize;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.resources.mapping.RemoteResourceMappingContext;
import org.eclipse.core.resources.mapping.ResourceMapping;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.egit.core.AdapterUtils;
import org.eclipse.egit.core.internal.util.ResourceUtil;
import org.eclipse.egit.core.synchronize.GitResourceVariantTreeSubscriber;
import org.eclipse.egit.core.synchronize.GitSubscriberMergeContext;
import org.eclipse.egit.core.synchronize.GitSubscriberResourceMappingContext;
import org.eclipse.egit.core.synchronize.dto.GitSynchronizeData;
import org.eclipse.egit.core.synchronize.dto.GitSynchronizeDataSet;
import org.eclipse.egit.ui.JobFamilies;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.team.core.subscribers.SubscriberScopeManager;
import org.eclipse.team.ui.TeamUI;
import org.eclipse.team.ui.synchronize.ISynchronizeParticipant;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class GitModelSynchronize {

	public static final void launch(GitSynchronizeData data,
			IResource[] resources) {
		launch(new GitSynchronizeDataSet(data), resources);
	}

	public static final void launch(final GitSynchronizeDataSet gsdSet,
			IResource[] resources) {
		ResourceMapping[] mappings = getGitResourceMappings(resources);

		launch(gsdSet, mappings);
	}

	public static final void launch(final GitSynchronizeDataSet gsdSet,
			ResourceMapping[] mappings) {
		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();

		fireSynchronizeAction(window, gsdSet, mappings);
	}

	private static ResourceMapping[] getGitResourceMappings(
			IResource[] elements) {
		List<ResourceMapping> gitMappings = new ArrayList<>();

		for (IResource element : elements) {
			ResourceMapping mapping = AdapterUtils.adapt(element,
					ResourceMapping.class);
			if (mapping != null && isMappedToGitProvider(mapping))
				gitMappings.add(mapping);
		}

		return gitMappings.toArray(new ResourceMapping[gitMappings.size()]);
	}

	private static boolean isMappedToGitProvider(ResourceMapping element) {
		IProject[] projects = element.getProjects();
		for (IProject project: projects) {
			if (ResourceUtil.isSharedWithGit(project)) {
				return true;
			}
		}
		return false;
	}

	private static void fireSynchronizeAction(final IWorkbenchWindow window,
			final GitSynchronizeDataSet gsdSet, final ResourceMapping[] mappings) {
		final GitResourceVariantTreeSubscriber subscriber = new GitResourceVariantTreeSubscriber(
				gsdSet);

		Job syncJob = new WorkspaceJob(
				UIText.GitModelSynchronize_fetchGitDataJobName) {

			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) {
				subscriber.init(monitor);

				return Status.OK_STATUS;
			}
			@Override
			public boolean belongsTo(Object family) {
				if (JobFamilies.SYNCHRONIZE_READ_DATA.equals(family))
					return true;

				return super.belongsTo(family);
			}
		};

		syncJob.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				RemoteResourceMappingContext remoteContext = new GitSubscriberResourceMappingContext(subscriber,
						gsdSet);
				SubscriberScopeManager manager = new SubscriberScopeManager(
						subscriber.getName(), mappings, subscriber,
						remoteContext, true);
				GitSubscriberMergeContext context = new GitSubscriberMergeContext(
						subscriber, manager, gsdSet);
				final GitModelSynchronizeParticipant participant = new GitModelSynchronizeParticipant(
						context);

				TeamUI.getSynchronizeManager().addSynchronizeParticipants(
						new ISynchronizeParticipant[] { participant });

				IWorkbenchPart activePart = null;
				if (window != null)
					activePart = window.getActivePage().getActivePart();

				participant.run(activePart);
			}
		});

		syncJob.setUser(true);
		syncJob.schedule();
	}

	private GitModelSynchronize() {
	}
}
