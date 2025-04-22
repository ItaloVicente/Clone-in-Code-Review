package org.eclipse.egit.core.internal.merge;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.mapping.RemoteResourceMappingContext;
import org.eclipse.core.resources.mapping.ResourceMapping;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.internal.CoreText;
import org.eclipse.egit.core.internal.job.RuleUtil;
import org.eclipse.egit.core.internal.storage.TreeParserResourceVariant;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.dircache.DirCacheBuildIterator;
import org.eclipse.jgit.dircache.DirCacheBuilder;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.merge.MergeResult;
import org.eclipse.jgit.merge.RecursiveMerger;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.WorkingTreeIterator;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;
import org.eclipse.osgi.util.NLS;
import org.eclipse.team.core.diff.IDiff;
import org.eclipse.team.core.mapping.IMergeContext;
import org.eclipse.team.core.mapping.IResourceMappingMerger;
import org.eclipse.team.core.mapping.ISynchronizationScopeManager;
import org.eclipse.team.core.subscribers.Subscriber;
import org.eclipse.team.core.subscribers.SubscriberMergeContext;
import org.eclipse.team.core.subscribers.SubscriberResourceMappingContext;
import org.eclipse.team.core.subscribers.SubscriberScopeManager;

public class RecursiveModelMerger extends RecursiveMerger {
	private final Set<String> makeInSync = new LinkedHashSet<String>();

	private final Set<String> handledPaths = new HashSet<String>();

	public RecursiveModelMerger(Repository db, boolean inCore) {
		super(db, inCore);
	}

	@Override
	protected boolean mergeTreeWalk(TreeWalk treeWalk) throws IOException {
		final GitResourceVariantTreeProvider variantTreeProvider = new TreeWalkResourceVariantTreeProvider(
				getRepository(), treeWalk, T_BASE, T_OURS, T_THEIRS);
		final GitResourceVariantTreeSubscriber subscriber = new GitResourceVariantTreeSubscriber(
				variantTreeProvider);
		final RemoteResourceMappingContext remoteMappingContext = new SubscriberResourceMappingContext(
				subscriber, true);

		try {
			refreshRoots(subscriber.roots());
		} catch (CoreException e) {
			Activator.logError(CoreText.RecursiveModelMerger_RefreshError, e);
			return super.mergeTreeWalk(treeWalk);
		}

		final LogicalModels logicalModels = new LogicalModels();
		logicalModels.build(variantTreeProvider.getKnownResources(),
				remoteMappingContext);

		while (treeWalk.next()) {
			if (treeWalk.getRawMode(T_BASE) == 0
					&& treeWalk.getRawMode(T_OURS) == 0
					&& treeWalk.getRawMode(T_THEIRS) == 0)
				continue;

			final String pathString = treeWalk.getPathString();
			if (handledPaths.contains(pathString)) {
				if (treeWalk.isSubtree() && enterSubtree)
					treeWalk.enterSubtree();
				if (!unmergedPaths.contains(pathString))
					makeInSync.add(pathString);
				continue;
			}

			final IResource resource = getResourceForGitPath(pathString);
			Set<IResource> logicalModel = logicalModels.getModel(resource);

			IResourceMappingMerger modelMerger = null;
			if (logicalModel != null) {
				try {
					modelMerger = LogicalModels.findAdapter(logicalModel,
							IResourceMappingMerger.class);
				} catch (CoreException e) {
				}
			}

			if (modelMerger != null) {
				assert logicalModel != null;
				enterSubtree = true;
				final IMergeContext mergeContext;
				try {
					mergeContext = prepareMergeContext(subscriber, logicalModel, remoteMappingContext);
				} catch (CoreException e) {
					final String message = NLS
							.bind(CoreText.RecursiveModelMerger_ScopeInitializationError,
									pathString);
					Activator.logError(message, e);
					cleanUp();
					return false;
				} catch (OperationCanceledException e) {
					final String message = NLS
							.bind(CoreText.RecursiveModelMerger_ScopeInitializationInterrupted,
									pathString);
					Activator.logError(message, e);
					cleanUp();
					return false;
				} catch (InterruptedException e) {
					final String message = NLS
							.bind(CoreText.RecursiveModelMerger_ScopeInitializationInterrupted,
									pathString);
					Activator.logError(message, e);
					cleanUp();
					return false;
				}
				try {
					final IStatus status = modelMerger.merge(mergeContext,
							new NullProgressMonitor());
					for (IResource handledFile : logicalModel) {
						final String filePath = handledFile.getFullPath()
								.toString().substring(1);
						modifiedFiles.add(filePath);
						handledPaths.add(filePath);

						if (status.getSeverity() != IStatus.OK) {
							unmergedPaths.add(filePath);
							mergeResults.put(
									filePath,
									new MergeResult<RawText>(Collections
											.<RawText> emptyList()));
							final TreeParserResourceVariant baseVariant = (TreeParserResourceVariant) subscriber.getBaseTree().getResourceVariant(handledFile);
							final TreeParserResourceVariant oursVariant = (TreeParserResourceVariant) subscriber.getSourceTree().getResourceVariant(handledFile);
							final TreeParserResourceVariant theirsVariant = (TreeParserResourceVariant) subscriber.getRemoteTree().getResourceVariant(handledFile);
							markConflict(filePath, builder, baseVariant,
									oursVariant, theirsVariant);
						}
					}
				} catch (CoreException e) {
					Activator.logError(e.getMessage(), e);
					cleanUp();
					return false;
				}
				if (treeWalk.isSubtree())
					enterSubtree = true;
			} else {
				if (!processEntry(
						treeWalk.getTree(T_BASE, CanonicalTreeParser.class),
						treeWalk.getTree(T_OURS, CanonicalTreeParser.class),
						treeWalk.getTree(T_THEIRS, CanonicalTreeParser.class),
						treeWalk.getTree(T_INDEX, DirCacheBuildIterator.class),
						(tw.getTreeCount() >= T_FILE) ? null : treeWalk
								.getTree(T_FILE, WorkingTreeIterator.class))) {
					cleanUp();
					return false;
				}
			}
			if (treeWalk.isSubtree() && enterSubtree)
				treeWalk.enterSubtree();
		}
		if (makeInSync.isEmpty())
			return true;

		final TreeWalk syncingTreeWalk = new TreeWalk(getRepository());
		syncingTreeWalk.addTree(new DirCacheIterator(dircache));
		syncingTreeWalk.addTree(new FileTreeIterator(getRepository()));
		syncingTreeWalk.setRecursive(true);
		syncingTreeWalk
				.setFilter(PathFilterGroup.createFromStrings(makeInSync));
		String lastAdded = null;
		while (syncingTreeWalk.next()) {
			String path = syncingTreeWalk.getPathString();
			if (path.equals(lastAdded))
				continue;

			WorkingTreeIterator workingTree = syncingTreeWalk.getTree(1,
					WorkingTreeIterator.class);
			DirCacheIterator dirCache = syncingTreeWalk.getTree(0,
					DirCacheIterator.class);
			if (dirCache == null && workingTree != null
					&& workingTree.isEntryIgnored()) {
			} else if (workingTree != null) {
				if (dirCache == null || dirCache.getDirCacheEntry() == null
						|| !dirCache.getDirCacheEntry().isAssumeValid()) {
					final DirCacheEntry dce = new DirCacheEntry(path);
					final FileMode mode = workingTree
							.getIndexFileMode(dirCache);
					dce.setFileMode(mode);

					if (FileMode.GITLINK != mode) {
						dce.setLength(workingTree.getEntryLength());
						dce.setLastModified(workingTree.getEntryLastModified());
						InputStream is = workingTree.openEntryStream();
						try {
							dce.setObjectId(getObjectInserter().insert(
									Constants.OBJ_BLOB,
									workingTree.getEntryContentLength(), is));
						} finally {
							is.close();
						}
					} else
						dce.setObjectId(workingTree.getEntryObjectId());
					builder.add(dce);
					lastAdded = path;
				} else
					builder.add(dirCache.getDirCacheEntry());
			} else if (dirCache != null
					&& FileMode.GITLINK == dirCache.getEntryFileMode())
				builder.add(dirCache.getDirCacheEntry());
		}

		return true;
	}

	private void refreshRoots(IResource[] resources) throws CoreException {
		for (IResource root : resources)
			root.refreshLocal(IResource.DEPTH_INFINITE,
					new NullProgressMonitor());
	}

	private static IResource getResourceForGitPath(String pathString) {
		final IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace()
				.getRoot();

		final IPath path = new Path(pathString);
		final IResource resource;
		if (path.segmentCount() > 1)
			resource = workspaceRoot.getFile(path);
		else
			resource = workspaceRoot.getProject(pathString);
		return resource;
	}

	private void markConflict(String filePath, DirCacheBuilder cacheBuilder,
			TreeParserResourceVariant baseVariant, TreeParserResourceVariant oursVariant,
			TreeParserResourceVariant theirsVariant) {
		add(filePath, cacheBuilder, baseVariant, DirCacheEntry.STAGE_1);
		add(filePath, cacheBuilder, oursVariant, DirCacheEntry.STAGE_2);
		add(filePath, cacheBuilder, theirsVariant, DirCacheEntry.STAGE_3);
	}

	private void add(String path, DirCacheBuilder cacheBuilder,
			TreeParserResourceVariant variant, int stage) {
		if (variant != null && !FileMode.TREE.equals(variant.getRawMode())) {
			DirCacheEntry e = new DirCacheEntry(path, stage);
			e.setFileMode(FileMode.fromBits(variant.getRawMode()));
			e.setObjectId(variant.getObjectId());
			e.setLastModified(0);
			e.setLength(0);
			cacheBuilder.add(e);
		}
	}

	private IMergeContext prepareMergeContext(Subscriber subscriber,
			Set<IResource> model, RemoteResourceMappingContext mappingContext)
			throws CoreException, OperationCanceledException,
			InterruptedException {
		final Set<ResourceMapping> allMappings = LogicalModels
				.getResourceMappings(model, mappingContext);
		final ResourceMapping[] mappings = allMappings
				.toArray(new ResourceMapping[allMappings.size()]);

		final ISynchronizationScopeManager manager = new SubscriberScopeManager(
				subscriber.getName(), mappings, subscriber, mappingContext,
				true) {
			@Override
			public ISchedulingRule getSchedulingRule() {
				return RuleUtil.getRule(getRepository());
			}
		};
		manager.initialize(new NullProgressMonitor());

		final IMergeContext context = new GitMergeContext(subscriber, manager);
		Job.getJobManager().join(context, new NullProgressMonitor());

		return context;
	}

	private class GitMergeContext extends SubscriberMergeContext {
		public GitMergeContext(Subscriber subscriber,
				ISynchronizationScopeManager scopeManager) {
			super(subscriber, scopeManager);
			initialize();
		}

		public void markAsMerged(IDiff node, boolean inSyncHint,
				IProgressMonitor monitor) throws CoreException {
			final IResource resource = getDiffTree().getResource(node);
			makeInSync.add(resource.getFullPath().toString().substring(1));
		}

		public void reject(IDiff diff, IProgressMonitor monitor)
				throws CoreException {
		}

		protected void makeInSync(IDiff diff, IProgressMonitor monitor)
				throws CoreException {
			final IResource resource = getDiffTree().getResource(diff);
			makeInSync.add(resource.getFullPath().toString().substring(1));
		}
	}
}
