
package org.eclipse.jgit.pgm;

import static org.eclipse.jgit.treewalk.TreeWalk.OperationType.CHECKOUT_OP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.StatusCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.ContentSource;
import org.eclipse.jgit.internal.diffmergetool.FileElement.Type;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheCheckout;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.dircache.DirCacheCheckout.CheckoutMetadata;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.internal.diffmergetool.ExternalMergeTool;
import org.eclipse.jgit.internal.diffmergetool.FileElement;
import org.eclipse.jgit.internal.diffmergetool.MergeTools;
import org.eclipse.jgit.internal.diffmergetool.ToolException;
import org.eclipse.jgit.lib.IndexDiff.StageState;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.WorkingTreeOptions;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.internal.BooleanTriState;
import org.eclipse.jgit.lib.CoreConfig.EolStreamType;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.eclipse.jgit.util.FS.ExecutionResult;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.RestOfArgumentsHandler;

@Command(name = "mergetool"
class MergeTool extends TextBuiltin {
	private MergeTools mergeTools;

	@Option(name = "--tool"
			"-t" }
	private String toolName;

	private BooleanTriState prompt = BooleanTriState.UNSET;

	@Option(name = "--prompt"
	void setPrompt(@SuppressWarnings("unused") boolean on) {
		prompt = BooleanTriState.TRUE;
	}

	@Option(name = "--no-prompt"
	void noPrompt(@SuppressWarnings("unused") boolean on) {
		prompt = BooleanTriState.FALSE;
	}

	@Option(name = "--tool-help"
	private boolean toolHelp;

	private BooleanTriState gui = BooleanTriState.UNSET;

	@Option(name = "--gui"
	void setGui(@SuppressWarnings("unused") boolean on) {
		gui = BooleanTriState.TRUE;
	}

	@Option(name = "--no-gui"
	void noGui(@SuppressWarnings("unused") boolean on) {
		gui = BooleanTriState.FALSE;
	}

	@Argument(required = false
	@Option(name = "--"
	protected List<String> filterPaths;

	private BufferedReader inputReader;

	@Override
	protected void init(Repository repository
		super.init(repository
		mergeTools = new MergeTools(repository);
		inputReader = new BufferedReader(new InputStreamReader(ins));
	}

	enum MergeResult {
		SUCCESSFUL
	}

	@Override
	protected void run() {
		try {
			if (toolHelp) {
				showToolHelp();
			} else {
				boolean showPrompt = mergeTools.isInteractive();
				if (prompt != BooleanTriState.UNSET) {
					showPrompt = prompt == BooleanTriState.TRUE;
				}
				String toolNameSelected = toolName;
				if ((toolNameSelected == null) || toolNameSelected.isEmpty()) {
					toolNameSelected = mergeTools.getDefaultToolName(gui);
				}
				Map<String
				if (files.size() > 0) {
					merge(files
				} else {
					outw.println(CLIText.get().mergeToolNoFiles);
				}
			}
			outw.flush();
		} catch (Exception e) {
			throw die(e.getMessage()
		}
	}

	private void merge(Map<String
			String toolNamePrompt) throws Exception {
		List<String> mergedFilePaths = new ArrayList<>(files.keySet());
		Collections.sort(mergedFilePaths);
		StringBuilder mergedFiles = new StringBuilder();
		for (String mergedFilePath : mergedFilePaths) {
			mergedFiles.append(MessageFormat.format("{0}\n"
		}
		outw.println(MessageFormat.format(CLIText.get().mergeToolMerging
				mergedFiles));
		outw.flush();
		MergeResult mergeResult = MergeResult.SUCCESSFUL;
		for (String mergedFilePath : mergedFilePaths) {
			if (mergeResult == MergeResult.FAILED) {
				if (showPrompt && !isContinueUnresolvedPaths()) {
					mergeResult = MergeResult.ABORTED;
				}
			}
			if (mergeResult == MergeResult.ABORTED) {
				break;
			}
			StageState fileState = files.get(mergedFilePath);
			if (fileState == StageState.BOTH_MODIFIED) {
				mergeResult = mergeModified(mergedFilePath
						toolNamePrompt);
			} else if ((fileState == StageState.DELETED_BY_US)
					|| (fileState == StageState.DELETED_BY_THEM)) {
				mergeResult = mergeDeleted(mergedFilePath
						fileState == StageState.DELETED_BY_US);
			} else {
				outw.println(MessageFormat.format(
						CLIText.get().mergeToolUnknownConflict
						mergedFilePath));
				mergeResult = MergeResult.ABORTED;
			}
		}
	}

	private MergeResult mergeModified(String mergedFilePath
			String toolNamePrompt) throws Exception {
		outw.println(MessageFormat.format(CLIText.get().mergeToolNormalConflict
				mergedFilePath));
		outw.flush();
		boolean launch = true;
		if (showPrompt) {
			launch = isLaunch(toolNamePrompt);
		}
		if (!launch) {
		}
		boolean isMergeSuccessful = true;
		ContentSource baseSource = ContentSource.create(db.newObjectReader());
		ContentSource localSource = ContentSource.create(db.newObjectReader());
		ContentSource remoteSource = ContentSource.create(db.newObjectReader());
		File tempDir = mergeTools.createTempDirectory();
		File tempFilesParent = tempDir != null ? tempDir : db.getWorkTree();
		try {
			FileElement base = null;
			FileElement local = null;
			FileElement remote = null;
			FileElement merged = new FileElement(mergedFilePath
					Type.MERGED);
			DirCache cache = db.readDirCache();
			try (RevWalk revWalk = new RevWalk(db);
					TreeWalk treeWalk = new TreeWalk(db
							revWalk.getObjectReader())) {
				treeWalk.setFilter(
						PathFilterGroup.createFromStrings(mergedFilePath));
				DirCacheIterator cacheIter = new DirCacheIterator(cache);
				treeWalk.addTree(cacheIter);
				while (treeWalk.next()) {
					if (treeWalk.isSubtree()) {
						treeWalk.enterSubtree();
						continue;
					}
					final EolStreamType eolStreamType = treeWalk
							.getEolStreamType(CHECKOUT_OP);
					final String filterCommand = treeWalk.getFilterCommand(
							Constants.ATTR_FILTER_TYPE_SMUDGE);
					WorkingTreeOptions opt = db.getConfig()
							.get(WorkingTreeOptions.KEY);
					CheckoutMetadata checkoutMetadata = new CheckoutMetadata(
							eolStreamType
					DirCacheEntry entry = treeWalk.getTree(DirCacheIterator.class).getDirCacheEntry();
					if (entry == null) {
						continue;
					}
					ObjectId id = entry.getObjectId();
					switch (entry.getStage()) {
					case DirCacheEntry.STAGE_1:
						base = new FileElement(mergedFilePath
						DirCacheCheckout.getContent(db
								checkoutMetadata
								baseSource.open(mergedFilePath
								new FileOutputStream(
										base.createTempFile(tempFilesParent)));
						break;
					case DirCacheEntry.STAGE_2:
						local = new FileElement(mergedFilePath
						DirCacheCheckout.getContent(db
								checkoutMetadata
								localSource.open(mergedFilePath
								new FileOutputStream(
										local.createTempFile(tempFilesParent)));
						break;
					case DirCacheEntry.STAGE_3:
						remote = new FileElement(mergedFilePath
						DirCacheCheckout.getContent(db
								checkoutMetadata
								remoteSource.open(mergedFilePath
								new FileOutputStream(remote
										.createTempFile(tempFilesParent)));
						break;
					}
				}
			}
			if ((local == null) || (remote == null)) {
				throw die(MessageFormat.format(CLIText.get().mergeToolDied
						mergedFilePath));
			}
			long modifiedBefore = merged.getFile().lastModified();
			try {
				ExecutionResult executionResult = mergeTools.merge(local
						remote
				outw.println(
						new String(executionResult.getStdout().toByteArray()));
				outw.flush();
				errw.println(
						new String(executionResult.getStderr().toByteArray()));
				errw.flush();
			} catch (ToolException e) {
				isMergeSuccessful = false;
				outw.println(e.getResultStdout());
				outw.flush();
				errw.println(MessageFormat.format(
						CLIText.get().mergeToolMergeFailed
				errw.flush();
				if (e.isCommandExecutionError()) {
					errw.println(e.getMessage());
					throw die(CLIText.get().mergeToolExecutionError
				}
			}
			if (isMergeSuccessful) {
				long modifiedAfter = merged.getFile().lastModified();
				if (modifiedBefore == modifiedAfter) {
					outw.println(MessageFormat.format(
							CLIText.get().mergeToolFileUnchanged
							mergedFilePath));
					isMergeSuccessful = !showPrompt || isMergeSuccessful();
				}
			}
			if (isMergeSuccessful) {
				addFile(mergedFilePath);
			}
		} finally {
			baseSource.close();
			localSource.close();
			remoteSource.close();
		}
		return isMergeSuccessful ? MergeResult.SUCCESSFUL : MergeResult.FAILED;
	}

	private MergeResult mergeDeleted(String mergedFilePath
			throws Exception {
		outw.println(MessageFormat.format(CLIText.get().mergeToolFileUnchanged
				mergedFilePath));
		if (deletedByUs) {
			outw.println(CLIText.get().mergeToolDeletedConflictByUs);
		} else {
			outw.println(CLIText.get().mergeToolDeletedConflictByThem);
		}
		int mergeDecision = getDeletedMergeDecision();
		if (mergeDecision == 1) {
			addFile(mergedFilePath);
		} else if (mergeDecision == -1) {
			rmFile(mergedFilePath);
		} else {
			return MergeResult.ABORTED;
		}
		return MergeResult.SUCCESSFUL;
	}

	private void addFile(String fileName) throws Exception {
		try (Git git = new Git(db)) {
			git.add().addFilepattern(fileName).call();
		}
	}

	private void rmFile(String fileName) throws Exception {
		try (Git git = new Git(db)) {
			git.rm().addFilepattern(fileName).call();
		}
	}

	private boolean hasUserAccepted(String message) throws IOException {
		boolean yes = true;
		outw.flush();
		BufferedReader br = inputReader;
		String line = null;
		while ((line = br.readLine()) != null) {
				yes = true;
				break;
				yes = false;
				break;
			}
			outw.print(message);
			outw.flush();
		}
		return yes;
	}

	private boolean isContinueUnresolvedPaths() throws IOException {
		return hasUserAccepted(CLIText.get().mergeToolContinueUnresolvedPaths);
	}

	private boolean isMergeSuccessful() throws IOException {
		return hasUserAccepted(CLIText.get().mergeToolWasMergeSuccessfull);
	}

	private boolean isLaunch(String toolNamePrompt) throws IOException {
		boolean launch = true;
		outw.print(MessageFormat.format(CLIText.get().mergeToolLaunch
		outw.flush();
		BufferedReader br = inputReader;
		String line = null;
		if ((line = br.readLine()) != null) {
				launch = false;
			}
		}
		return launch;
	}

	private int getDeletedMergeDecision() throws IOException {
		final String message = CLIText.get().mergeToolDeletedMergeDecision
		outw.print(message);
		outw.flush();
		BufferedReader br = inputReader;
		String line = null;
		while ((line = br.readLine()) != null) {
				break;
				break;
				break;
			}
			outw.print(message);
			outw.flush();
		}
		return ret;
	}

	private void showToolHelp() throws IOException {
		StringBuilder availableToolNames = new StringBuilder();
		for (String name : mergeTools.getAvailableTools().keySet()) {
			availableToolNames.append(MessageFormat.format("\t\t{0}\n"
		}
		StringBuilder notAvailableToolNames = new StringBuilder();
		for (String name : mergeTools.getNotAvailableTools().keySet()) {
			notAvailableToolNames
					.append(MessageFormat.format("\t\t{0}\n"
		}
		StringBuilder userToolNames = new StringBuilder();
		Map<String
				.getUserDefinedTools();
		for (String name : userTools.keySet()) {
			userToolNames.append(MessageFormat.format("\t\t{0}.cmd {1}\n"
					name
		}
		outw.println(MessageFormat.format(
				CLIText.get().mergeToolHelpSetToFollowing
				userToolNames
	}

	private Map<String
			NoWorkTreeException
		Map<String
		try (Git git = new Git(db)) {
			StatusCommand statusCommand = git.status();
			if (filterPaths != null && filterPaths.size() > 0) {
				for (String path : filterPaths) {
					statusCommand.addPath(path);
				}
			}
			Status status = statusCommand.call();
			files = status.getConflictingStageState();
		}
		return files;
	}

}
