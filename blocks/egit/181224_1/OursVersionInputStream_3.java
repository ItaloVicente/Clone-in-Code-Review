package org.eclipse.egit.core.internal.efs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.MessageFormat;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceStatus;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.internal.efs.EgitFileSystem.UriComponents;
import org.eclipse.jgit.lib.Repository;

public enum HiddenResources {


	INSTANCE;

	private static final String PROJECT_NAME = ".org.eclipse.egit.core.cmp"; //$NON-NLS-1$

	private static final String SRC_FOLDER_PREFIX = "src"; //$NON-NLS-1$

	private final static String PROJECT_FILE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" //$NON-NLS-1$
			+ "<projectDescription>\n" //$NON-NLS-1$
			+ "\t<name>" + PROJECT_NAME + "</name>\n" //$NON-NLS-1$ //$NON-NLS-2$
			+ "\t<comment></comment>\n" //$NON-NLS-1$
			+ "\t<projects>\n" //$NON-NLS-1$
			+ "\t</projects>\n" //$NON-NLS-1$
			+ "\t<buildSpec>\n" //$NON-NLS-1$
			+ "\t</buildSpec>\n" //$NON-NLS-1$
			+ "\t<natures>\n" //$NON-NLS-1$
			+ "\t</natures>\n" //$NON-NLS-1$
			+ "</projectDescription>"; //$NON-NLS-1$

	private boolean initialized;

	public IFile createFile(URI uri, String name, Charset encoding,
			IProgressMonitor monitor) throws CoreException {
		SubMonitor progress = SubMonitor.convert(monitor, 3);
		IProject project = getHiddenProject(progress.newChild(1));
		initialize(progress.newChild(1));
		IResource[] children = project.members();
		progress.setWorkRemaining(children.length + 2);
		for (IResource rsc : children) {
			if (rsc.getType() == IResource.FOLDER) {
				try {
					return linkFile((IFolder) rsc, uri, name, encoding,
							progress.newChild(1));
				} catch (CoreException e) {
				}
			} else {
				progress.worked(1);
			}
		}
		IFolder newFolder = createFolder(project, children.length,
				progress.newChild(1));
		return linkFile(newFolder, uri, name, encoding, progress.newChild(1));
	}

	public void initialize(IProgressMonitor monitor) {
		SubMonitor progress = SubMonitor.convert(monitor, 2);
		try {
			IProject project = getHiddenProject(progress.newChild(1));
			initialize(project, progress.newChild(1));
		} catch (CoreException e) {
			Activator.logWarning("Cannot clean up internal hidden project", e); //$NON-NLS-1$
		}
	}

	private synchronized void initialize(IProject project,
			IProgressMonitor monitor) {
		if (initialized) {
			return;
		}
		initialized = true;
		IWorkspaceRunnable clean = m -> {
			IResource[] resources = project.members();
			SubMonitor progress = SubMonitor.convert(m, resources.length);
			for (IResource rsc : project.members()) {
				if (rsc.getType() == IResource.FOLDER) {
					IResource[] children = ((IFolder) rsc).members();
					SubMonitor sub = SubMonitor.convert(progress.newChild(1),
							children.length);
					for (IResource f : children) {
						if (f.isLinked()) {
							try {
								f.delete(true, sub.newChild(1));
							} catch (CoreException e) {
								Activator.logWarning(MessageFormat.format(
										"Cannot clean up internal hidden resource {}", //$NON-NLS-1$
										f), e);
							}
						}
						if (sub.isCanceled()) {
							return;
						}
					}
					children = ((IFolder) rsc).members();
					if (children.length == 0) {
						try {
							rsc.delete(true, null);
						} catch (CoreException e) {
							Activator.logWarning(MessageFormat.format(
									"Cannot clean up internal hidden folder {}", //$NON-NLS-1$
									rsc), e);
						}
					}
				} else {
					progress.worked(1);
				}
				if (progress.isCanceled()) {
					return;
				}
			}
		};
		try {
			project.getWorkspace().run(clean, null, IWorkspace.AVOID_UPDATE,
					monitor);
		} catch (CoreException e) {
			Activator.logWarning(MessageFormat.format(
					"Cannot clean up internal hidden project {}", project), e); //$NON-NLS-1$
		}
	}

	public boolean isHiddenProject(IResource resource) {
		if (resource.getType() != IResource.PROJECT) {
			return false;
		}
		return PROJECT_NAME.equals(resource.getName());
	}

	public Repository getRepository(URI uri) {
		if (!EgitFileSystem.SCHEME.equals(uri.getScheme())) {
			return null;
		}
		try {
			return UriComponents.parse(uri).getRepository();
		} catch (URISyntaxException e) {
			return null;
		}
	}

	public String getGitPath(URI uri) {
		if (!EgitFileSystem.SCHEME.equals(uri.getScheme())) {
			return null;
		}
		try {
			return UriComponents.parse(uri).getGitPath();
		} catch (URISyntaxException e) {
			return null;
		}
	}

	public String getGitPath(URI uri, Repository repository) {
		if (!EgitFileSystem.SCHEME.equals(uri.getScheme())) {
			return null;
		}
		try {
			UriComponents parsed = UriComponents.parse(uri);
			if (parsed.getRepoDir().equals(repository.getDirectory())) {
				return parsed.getGitPath();
			}
		} catch (URISyntaxException e) {
		}
		return null;
	}

	private IProject getHiddenProject(IProgressMonitor monitor) throws CoreException {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJECT_NAME);
		if (!project.isAccessible()) {
			SubMonitor progress = SubMonitor.convert(monitor, 2);
			if (!project.exists()) {
				createProject(project, progress.newChild(1));
			}
			progress.setWorkRemaining(1);
			openProject(project, progress.newChild(1));
		}
		return project;
	}

	private void createProject(IProject project, IProgressMonitor monitor)
			throws CoreException {
		IProjectDescription desc = project.getWorkspace()
				.newProjectDescription(project.getName());
		IPath stateLocation = Activator.getDefault().getStateLocation();
		desc.setLocation(stateLocation.append(PROJECT_NAME));
		project.create(desc, IResource.HIDDEN, monitor);
	}

	private void openProject(IProject project, IProgressMonitor monitor)
			throws CoreException {
		SubMonitor progress = SubMonitor.convert(monitor, 1);
		try {
			project.open(progress.newChild(1));
		} catch (CoreException e) {
			progress.setWorkRemaining(3);
			if (e.getStatus()
					.getCode() == IResourceStatus.FAILED_READ_METADATA) {
				project.delete(false, true, progress.newChild(1));
				createProject(project, progress.newChild(1));
			} else {
				IPath stateLocation = Activator.getDefault().getStateLocation();
				IPath projectPath = stateLocation.append(PROJECT_NAME);
				File directory = projectPath.toFile();
				try {
					if (!directory.mkdirs() && !directory.isDirectory()) {
						throw new FileNotFoundException();
					}
					Files.write(
							projectPath.append(
									IProjectDescription.DESCRIPTION_FILE_NAME)
									.toFile().toPath(),
							PROJECT_FILE.getBytes(StandardCharsets.UTF_8));
					progress.worked(2);
				} catch (IOException ioe) {
					project.delete(true, true, progress.newChild(1));
					createProject(project, progress.newChild(1));
				}
			}
			project.open(progress.newChild(1));
		}
	}

	private IFolder createFolder(IProject project, int n,
			IProgressMonitor monitor) throws CoreException {
		IFolder folder = project.getFolder(SRC_FOLDER_PREFIX + n);
		folder.create(IResource.NONE, true, monitor);
		return folder;
	}

	private IFile linkFile(IFolder folder, URI uri, String name,
			Charset encoding, IProgressMonitor monitor) throws CoreException {
		SubMonitor progress = SubMonitor.convert(monitor, 2);
		IFile file = folder.getFile(name);
		file.createLink(uri, IResource.NONE, progress.newChild(1));
		if (encoding != null) {
			file.setCharset(encoding.name(), progress.newChild(1));
		}
		return file;
	}
}
