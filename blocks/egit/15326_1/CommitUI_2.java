package org.eclipse.egit.ui.internal.commit;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.egit.core.internal.util.ResourceUtil;
import org.eclipse.egit.core.project.RepositoryMapping;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIPreferences;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.swt.widgets.Shell;

public class CommitErrorWarningsUtil {

	private static boolean doesResourceContainMarkerOfSeverity(int severity,
			IResource resource) {
		try {
			IMarker[] markers = resource.findMarkers(IMarker.PROBLEM, true,
					IResource.DEPTH_INFINITE);
			for (IMarker marker : markers) {
				if (marker
						.getAttribute(IMarker.SEVERITY, IMarker.SEVERITY_INFO) == severity) {
					return true;
				}
			}
			return false;
		} catch (CoreException e) {
			e.printStackTrace();
			return false;
		}
	}

	private static boolean doesResourceContainMarkerOfSeverity(int severity,
			IResource... resources) {
		for (IResource resource : resources) {
			if (doesResourceContainMarkerOfSeverity(severity, resource)) {
				return true;
			}
		}
		return false;
	}

	public static boolean canCommitWithCurrentErrors(Shell parent,
			Repository repo, Collection<String> selectedFiles) {
		IPreferenceStore preferences = Activator.getDefault()
				.getPreferenceStore();
		if (preferences.getInt(UIPreferences.COMMIT_WITH_ERRORS_ACTION) != 0) {
			boolean errorsInScope = false;
			switch (preferences.getInt(UIPreferences.COMMIT_WITH_ERRORS_SCOPE)) {
			case 0: // committed resources
				errorsInScope = doesResourceContainMarkerOfSeverity(
						IMarker.SEVERITY_ERROR,
						getResources(repo, selectedFiles));
				break;
			case 1: // resources in repository
				errorsInScope = doesResourceContainMarkerOfSeverity(
						IMarker.SEVERITY_ERROR, getProjectsOfRepositories(repo));
				break;
			default:
				errorsInScope = doesResourceContainMarkerOfSeverity(
						IMarker.SEVERITY_ERROR, ResourcesPlugin.getWorkspace()
								.getRoot());
			}
			if (errorsInScope) {
				if (preferences.getInt(UIPreferences.COMMIT_WITH_ERRORS_ACTION) == 1) {
					MessageDialog.openError(parent,
							UIText.CommitDialog_ErrorsExistInScope_Title,
							UIText.CommitDialog_ErrorsExistInScope_Denied);
					return false;
				}
				if (!MessageDialog.openQuestion(parent,
						UIText.CommitDialog_ErrorsExistInScope_Title,
						UIText.CommitDialog_ErrorsExistInScope_WantCommit)) {
					return false;
				}
			}
		}
		return true;
	}

	public static boolean canCommitWithCurrentWarnings(Shell parent,
			Repository repo, Collection<String> selectedFiles) {
		IPreferenceStore preferences = Activator.getDefault()
				.getPreferenceStore();
		if (preferences.getInt(UIPreferences.COMMIT_WITH_WARNINGS_ACTION) != 0) {
			boolean warningsInScope = false;
			switch (preferences.getInt(UIPreferences.COMMIT_WITH_ERRORS_SCOPE)) {
			case 0: // committed resources
				warningsInScope = doesResourceContainMarkerOfSeverity(
						IMarker.SEVERITY_WARNING,
						getResources(repo, selectedFiles));
				break;
			case 1: // resources in repository
				warningsInScope = doesResourceContainMarkerOfSeverity(
						IMarker.SEVERITY_WARNING,
						getProjectsOfRepositories(repo));
				break;
			default:
				warningsInScope = doesResourceContainMarkerOfSeverity(
						IMarker.SEVERITY_WARNING, ResourcesPlugin
								.getWorkspace().getRoot());
			}
			if (warningsInScope) {
				if (preferences
						.getInt(UIPreferences.COMMIT_WITH_WARNINGS_ACTION) == 1) {
					MessageDialog.openError(parent,
							UIText.CommitDialog_WarningsExistInScope_Title,
							UIText.CommitDialog_WarningsExistInScope_Denied);
					return false;
				}
				if (!MessageDialog.openQuestion(parent,
						UIText.CommitDialog_WarningsExistInScope_Title,
						UIText.CommitDialog_WarningsExistInScope_WantCommit)) {
					return false;
				}
			}
		}
		return true;
	}

	private static IResource[] getResources(Repository repo,
			Collection<String> fileNames) {
		Collection<IResource> resources = new HashSet<IResource>();
		for (String fileName : fileNames) {
			resources.add(ResourceUtil.getFileForLocation(repo, fileName));
		}
		return resources.toArray(new IResource[resources.size()]);
	}

	static IProject[] getProjectsOfRepositories(Repository repo) {
		Set<IProject> ret = new HashSet<IProject>();
		final IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
				.getProjects();
		for (IProject project : projects) {
			RepositoryMapping mapping = RepositoryMapping.getMapping(project);
			if (mapping != null && mapping.getRepository() == repo)
				ret.add(project);
		}
		return ret.toArray(new IProject[ret.size()]);
	}
}
