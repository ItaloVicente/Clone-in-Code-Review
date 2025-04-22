package org.eclipse.egit.ui.internal.synchronize;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.egit.core.project.RepositoryMapping;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIText;
import org.eclipse.jgit.lib.AbstractIndexTreeVisitor;
import org.eclipse.jgit.lib.Commit;
import org.eclipse.jgit.lib.IndexTreeWalker;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.Tree;
import org.eclipse.jgit.lib.TreeEntry;
import org.eclipse.jgit.lib.GitIndex.Entry;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevCommitList;
import org.eclipse.jgit.revwalk.RevSort;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.filter.AndTreeFilter;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.eclipse.osgi.util.NLS;
import org.eclipse.team.core.TeamException;
import org.eclipse.team.core.variants.AbstractResourceVariantTree;
import org.eclipse.team.core.variants.IResourceVariant;

abstract class GitResourceVariantTree extends AbstractResourceVariantTree {

	private Map<IProject, Repository> repositories = new HashMap<IProject, Repository>();

	private Map<String, RevCommitList<RevCommit>> dates = new HashMap<String, RevCommitList<RevCommit>>();

	private Map<String, ObjectId> updated = new HashMap<String, ObjectId>();

	private Map<Repository, Tree> trees = new HashMap<Repository, Tree>();

	private IResource[] roots;

	GitResourceVariantTree(IResource[] roots) {
		this.roots = roots;

		for (IResource root : roots) {
			RepositoryMapping mapping = RepositoryMapping.getMapping(root);
			Repository repository = mapping.getRepository();
			Assert.isNotNull(repository);
			repositories.put(root.getProject(), repository);
		}
	}

	public IResource[] roots() {
		return roots;
	}

	public IResource[] members(IResource resource) throws TeamException {
		if (resource.exists() && resource instanceof IContainer) {
			try {
				return ((IContainer) resource).members();
			} catch (CoreException e) {
				throw new TeamException(e.getStatus());
			}
		}
		return new IResource[0];
	}

	private boolean contains(File file) {
		for (IResource root : roots) {
			if (file.getAbsolutePath().startsWith(
					root.getLocation().toFile().getAbsolutePath())) {
				return true;
			}
		}
		return false;
	}

	abstract String getRevString(IResource resource);

	private synchronized void initialize(IResource resource) throws IOException {
		Repository db = repositories.get(resource.getProject());
		if (!trees.containsKey(db)) {
			Commit commit = db.mapCommit(getRevString(resource));
			Tree tree = commit.getTree();

			trees.put(db, tree);
			walk(db, commit, tree);
		}
	}

	private void walk(final Repository db, final Commit commit, Tree merge)
			throws IOException {
		IndexTreeWalker walker = new IndexTreeWalker(db.getIndex(), merge, db.getWorkDir(),
				new AbstractIndexTreeVisitor() {
					public void visitEntry(TreeEntry treeEntry,
							Entry indexEntry, File file) throws IOException {
						if (treeEntry != null && contains(file)) {
							store(db, commit, treeEntry);
						}
					}
				});
		walker.walk();
	}

	private void store(Repository db, Commit commit, TreeEntry treeEntry)
			throws IOException {
		String entry = treeEntry.getFullName();
		RevWalk walk = new RevWalk(db);
		walk.sort(RevSort.COMMIT_TIME_DESC, true);
		walk.sort(RevSort.BOUNDARY, true);
		walk.markStart(walk.parseCommit(commit.getCommitId()));
		walk.setTreeFilter(AndTreeFilter.create(PathFilterGroup
				.createFromStrings(Collections.singleton(entry)),
				TreeFilter.ANY_DIFF));

		RevCommitList<RevCommit> list = new RevCommitList<RevCommit>();
		list.source(walk);

		int lastSize = 0;
		do {
			lastSize = list.size();
			list.fillTo(Integer.MAX_VALUE);
		} while (lastSize != list.size());

		dates.put(entry, list);
		updated.put(entry, treeEntry.getId());
	}

	public IResourceVariant getResourceVariant(IResource resource)
			throws TeamException {
		return fetchVariant(resource, 0, new NullProgressMonitor());
	}

	private IResourceVariant findFolderVariant(IResource resource,
			Repository repository) {
		File workDir = repository.getWorkDir();
		File resourceLocation = resource.getLocation().toFile();
		String resLocationAbsolutePath = resourceLocation.getAbsolutePath();

		for (Map.Entry<String, ObjectId> entry : updated.entrySet()) {
			String entryName = entry.getKey();
			File file = new File(workDir, entryName);

			if (file.getAbsolutePath().startsWith(resLocationAbsolutePath)) {
				return new GitFolderResourceVariant(resource);
			}

		}

		return null;
	}

	private IResourceVariant findFileVariant(IResource resource,
			Repository repository) throws TeamException {
		String gitPath = RepositoryMapping.getMapping(resource)
				.getRepoRelativePath(resource);
		ObjectId objectId = updated.get(gitPath);
		if (objectId != null) {
			File root = repository.getWorkDir();
			File file = new File(root, gitPath);

			if (resource.getLocation().toFile().equals(file)) {
				try {
					Tree merge = trees.get(repository);
					TreeEntry te = merge.findBlobMember(gitPath);
					GitBlobResourceVariant variant = new GitBlobResourceVariant(
							resource, repository, te.getId(), dates
									.get(gitPath));
					return variant;
				} catch (IOException e) {
					throw new TeamException(new Status(IStatus.ERROR, Activator
							.getPluginId(), NLS.bind(
							UIText.GitResourceVariantTree_couldNotFindBlob,
							gitPath), e));
				}
			}
		}
		return null;
	}

	public boolean hasResourceVariant(IResource resource) throws TeamException {
		return getResourceVariant(resource) != null;
	}

	public void flushVariants(IResource resource, int depth)
			throws TeamException {

	}

	@Override
	protected IResourceVariant[] fetchMembers(IResourceVariant variant,
			IProgressMonitor progress) throws TeamException {
		if (!variant.isContainer()) {
			return new IResourceVariant[0];
		}

		IProgressMonitor monitor = SubMonitor.convert(progress);

		Set<IResourceVariant> members = new HashSet<IResourceVariant>();
		try {
			GitFolderResourceVariant folderVariant = (GitFolderResourceVariant) variant;
			IContainer container = folderVariant.getContainer();
			File resourceLocation = container.getLocation().toFile();
			Repository repository = repositories.get(container.getProject());

			monitor.beginTask(NLS.bind(
					UIText.GitResourceVariantTree_fetchingMembers, container
							.getLocation()), updated.size());
			File root = repository.getWorkDir();

			for (Map.Entry<String, ObjectId> entry : updated.entrySet()) {
				String entryName = entry.getKey();
				File file = new File(root, entryName);

				if (file.getAbsolutePath().startsWith(
						resourceLocation.getAbsolutePath())) {
					members.add(getMember(container, repository, entryName));
				}

				monitor.worked(1);
			}
		} finally {
			monitor.done();
		}
		return members.toArray(new IResourceVariant[members.size()]);
	}

	private IResourceVariant getMember(IContainer container,
			Repository repository, String entryName) throws TeamException {
		String gitPath = RepositoryMapping.getMapping(container)
				.getRepoRelativePath(container);
		String memberName = entryName.substring(gitPath.length() + 1);
		int index = memberName.indexOf('/');
		if (index == -1) {
			Tree merge = trees.get(repository);
			try {
				TreeEntry te = merge.findBlobMember(entryName);
				GitBlobResourceVariant blobVariant = new GitBlobResourceVariant(
						container.getFile(new Path(memberName)), repository, te
								.getId(), dates.get(entryName));
				return blobVariant;
			} catch (IOException e) {
				throw new TeamException(new Status(IStatus.ERROR, Activator
						.getPluginId(), NLS
						.bind(UIText.GitResourceVariantTree_couldNotFindBlob,
								gitPath), e));
			}
		}

		String folderName = memberName.substring(0, index);
		IResourceVariant memberVariant = new GitFolderResourceVariant(container
				.getFolder(new Path(folderName)));
		return memberVariant;
	}

	private IResourceVariant fetchVariant(IResource resource,
			IProgressMonitor monitor) throws TeamException {
		try {
			monitor.beginTask(NLS.bind(
					UIText.GitResourceVariantTree_fetchingVariant, resource
							.getLocation()), 5);
			initialize(resource);
			monitor.worked(4);
		} catch (IOException e) {
			throw new TeamException(new Status(IStatus.ERROR, Activator
					.getPluginId(), NLS.bind(
					UIText.GitResourceVariantTree_unableToReadRepository,
					resource.getName()), e));
		}

		Repository repository = repositories.get(resource.getProject());

		if (resource instanceof IProject) {
			return new GitFolderResourceVariant(resource);
		} else if (resource instanceof IFolder) {
			return findFolderVariant(resource, repository);
		}

		return findFileVariant(resource, repository);
	}

	@Override
	protected IResourceVariant fetchVariant(IResource resource, int depth,
			IProgressMonitor monitor) throws TeamException {
		try {
			return fetchVariant(resource, monitor);
		} finally {
			monitor.done();
		}
	}

	@Override
	protected boolean setVariant(IResource local, IResourceVariant remote)
			throws TeamException {
		return true;
	}

}
