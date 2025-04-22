package org.eclipse.egit.ui.internal.clone;

import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.egit.core.op.ConnectProviderOperation;
import org.eclipse.egit.ui.UIText;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.IWorkingSetManager;
import org.eclipse.ui.PlatformUI;

public class ProjectUtils {
	public static void createProjects(
			final Set<ProjectRecord> projectsToCreate,
			final Repository repository,
			final IWorkingSet[] selectedWorkingSets, IProgressMonitor monitor)
			throws CoreException, OperationCanceledException {
		IWorkspaceRunnable wsr = new IWorkspaceRunnable() {
			public void run(IProgressMonitor actMonitor) throws CoreException {
				IWorkingSetManager workingSetManager = PlatformUI
						.getWorkbench().getWorkingSetManager();
				try {
					actMonitor.beginTask("", projectsToCreate.size()); //$NON-NLS-1$
					if (actMonitor.isCanceled())
						throw new OperationCanceledException();
					for (ProjectRecord projectRecord : projectsToCreate) {
						if (actMonitor.isCanceled())
							throw new OperationCanceledException();
						actMonitor.setTaskName(projectRecord.getProjectLabel());
						IProject project = createExistingProject(projectRecord,
								new SubProgressMonitor(actMonitor, 1));
						if (repository != null) {
							ConnectProviderOperation connectProviderOperation = new ConnectProviderOperation(
									project, repository.getDirectory());
							connectProviderOperation.execute(actMonitor);
						}
						if (selectedWorkingSets != null
								&& selectedWorkingSets.length > 0)
							workingSetManager.addToWorkingSets(project,
									selectedWorkingSets);
					}
				} finally {
					actMonitor.done();
				}
			}
		};
		ResourcesPlugin.getWorkspace().run(wsr, monitor);
	}

	private static IProject createExistingProject(final ProjectRecord record,
			IProgressMonitor monitor) throws CoreException {
		String projectName = record.getProjectName();
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IProject project = workspace.getRoot().getProject(projectName);
		if (record.getProjectDescription() == null) {
			record.setProjectDescription(workspace
					.newProjectDescription(projectName));
			IPath locationPath = new Path(record.getProjectSystemFile()
					.getAbsolutePath());

			if (Platform.getLocation().isPrefixOf(locationPath))
				record.getProjectDescription().setLocation(null);
			else
				record.getProjectDescription().setLocation(locationPath);
		} else
			record.getProjectDescription().setName(projectName);

		try {
			monitor.beginTask(
					UIText.WizardProjectsImportPage_CreateProjectsTask, 100);
			project.create(record.getProjectDescription(),
					new SubProgressMonitor(monitor, 30));
			project.open(IResource.BACKGROUND_REFRESH, new SubProgressMonitor(
					monitor, 50));
			return project;
		} finally {
			monitor.done();
		}
	}
}
