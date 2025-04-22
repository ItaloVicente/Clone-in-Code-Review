package org.eclipse.egit.core.test;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.internal.indexdiff.GitResourceDeltaVisitor;
import org.eclipse.egit.core.project.RepositoryMapping;
import org.eclipse.jgit.lib.Repository;

public class GitResourceDeltaTestHelper {
	private Repository repository;

	private IResourceChangeListener resourceChangeListener;

	private final Collection<IResource> changedResources;

	private final boolean ignoreTeamPrivateMember;

	public GitResourceDeltaTestHelper(Repository repository) {
		this(repository, true);
	}

	public GitResourceDeltaTestHelper(Repository repository,
			boolean ignoreTeamPrivateMember) {
		this.repository = repository;
		this.changedResources = new HashSet<IResource>();
		this.ignoreTeamPrivateMember = ignoreTeamPrivateMember;
	}

	public void setUp() {
		resourceChangeListener = new IResourceChangeListener() {
			public void resourceChanged(final IResourceChangeEvent event) {
				try {
					event.getDelta().accept(new IResourceDeltaVisitor() {
						public boolean visit(IResourceDelta delta)
								throws CoreException {
							final IResource resource = delta.getResource();
							IProject project = resource.getProject();
							if (project == null)
								return true;
							RepositoryMapping mapping = RepositoryMapping
									.getMapping(resource);
							if (mapping == null)
								return true;
							if (mapping.getRepository() != repository)
								return false;
							GitResourceDeltaVisitor visitor = new GitResourceDeltaVisitor(
									repository);
							try {
								event.getDelta().accept(visitor);
							} catch (CoreException e) {
								Activator.logError(e.getMessage(), e);
								return false;
							}
							IPath gitDirAbsolutePath = mapping
									.getGitDirAbsolutePath();
							for (IResource res : visitor.getResourcesToUpdate()) {
								if (ignoreTeamPrivateMember
										&& (res.isTeamPrivateMember() || gitDirAbsolutePath
												.isPrefixOf(res
														.getRawLocation()
														.makeAbsolute())))
									continue;
								changedResources.add(res);
							}
							return false;
						}
					});
				} catch (CoreException e) {
					Activator.logError(e.getMessage(), e);
					return;
				}
			}
		};
		ResourcesPlugin.getWorkspace().addResourceChangeListener(
				resourceChangeListener, IResourceChangeEvent.POST_CHANGE);
	}

	public void tearDown() {
		if (resourceChangeListener != null) {
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(
					resourceChangeListener);
			resourceChangeListener = null;
		}
	}

	public Collection<IResource> getChangedResources() {
		return changedResources;
	}

	public boolean noChangedResources() {
		return changedResources.isEmpty();
	}

	public boolean anyChangedResources() {
		return !changedResources.isEmpty();
	}

	public void assertChangedResources(String[] expected) {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		for (String file : expected)
			assertThat(changedResources, hasItem(root.findMember(file)));
		System.out.println("Changed file in " + repository.toString());
		for (IResource file : changedResources)
			System.out.println("  " + file.toString());
		assertEquals(expected.length, changedResources.size());
	}
}
