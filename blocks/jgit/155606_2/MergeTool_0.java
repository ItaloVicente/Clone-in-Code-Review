
package org.eclipse.jgit.pgm;

import static org.eclipse.jgit.lib.Constants.HEAD;
import static org.eclipse.jgit.treewalk.TreeWalk.OperationType.CHECKOUT_OP;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.eclipse.jgit.diff.ContentSource;
import org.eclipse.jgit.diff.ContentSource.Pair;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffEntry.Side;
import org.eclipse.jgit.diffmergetool.ToolException;
import org.eclipse.jgit.diffmergetool.DiffToolManager;
import org.eclipse.jgit.diffmergetool.FileElement;
import org.eclipse.jgit.diffmergetool.IDiffTool;
import org.eclipse.jgit.diffmergetool.PromptContinueHandler;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.dircache.DirCacheCheckout;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.dircache.DirCacheCheckout.CheckoutMetadata;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.eclipse.jgit.lib.CoreConfig.EolStreamType;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.eclipse.jgit.pgm.opt.PathTreeFilterHandler;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.WorkingTreeIterator;
import org.eclipse.jgit.treewalk.WorkingTreeOptions;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.eclipse.jgit.util.FS.ExecutionResult;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

@Command(name = "difftool"
class DiffTool extends TextBuiltin {
	private DiffFormatter diffFmt;

	private DiffToolManager diffToolMgr;

	@Argument(index = 0
	private AbstractTreeIterator oldTree;

	@Argument(index = 1
	private AbstractTreeIterator newTree;

	private Optional<String> toolName = Optional.empty();

	@Option(name = "--tool"
			"-t" }
	void setToolName(String name) {
		toolName = Optional.of(name);
	}

	@Option(name = "--cached"
	private boolean cached;

	private Optional<Boolean> prompt = Optional.empty();

	@Option(name = "--prompt"
	void setPrompt(@SuppressWarnings("unused") boolean on) {
		prompt = Optional.of(Boolean.TRUE);
	}

	@Option(name = "--no-prompt"
	void noPrompt(@SuppressWarnings("unused") boolean on) {
		prompt = Optional.of(Boolean.FALSE);
	}

	@Option(name = "--tool-help"
	private boolean toolHelp;

	private boolean gui = false;

	@Option(name = "--gui"
	void setGui(@SuppressWarnings("unused") boolean on) {
		gui = true;
	}

	@Option(name = "--no-gui"
	void noGui(@SuppressWarnings("unused") boolean on) {
		gui = false;
	}

	private Optional<Boolean> trustExitCode = Optional.empty();

	@Option(name = "--trust-exit-code"
	void setTrustExitCode(@SuppressWarnings("unused") boolean on) {
		trustExitCode = Optional.of(Boolean.TRUE);
	}

	@Option(name = "--no-trust-exit-code"
	void noTrustExitCode(@SuppressWarnings("unused") boolean on) {
		trustExitCode = Optional.of(Boolean.FALSE);
	}

	@Option(name = "--"
	private TreeFilter pathFilter = TreeFilter.ALL;

	@Override
	protected void init(Repository repository
		super.init(repository
		diffFmt = new DiffFormatter(new BufferedOutputStream(outs));
		diffToolMgr = new DiffToolManager(repository);
	}

	@Override
	protected void run() {
		try {
			if (toolHelp) {
				showToolHelp();
			} else {
				List<DiffEntry> files = getFiles();
				if (files.size() > 0) {
					compare(files);
				}
			}
		} catch (RevisionSyntaxException | IOException e) {
			throw die(e.getMessage()
		} finally {
			diffFmt.close();
		}
	}

	private void informUserNoTool(List<String> tools) {
		try {
			outw.println(
			outw.println(
			outw.println(

			for (String name : tools) {
			}
			outw.println();
			outw.flush();
		} catch (IOException e) {
			throw new IllegalStateException("Cannot output text"
		}
	}

	private class CountingPromptContinueHandler implements PromptContinueHandler {
		private final int fileIndex;

		private final int fileCount;

		private final String fileName;

		public CountingPromptContinueHandler(int fileIndex
				String fileName) {
			this.fileIndex = fileIndex;
			this.fileCount = fileCount;
			this.fileName = fileName;
		}

		@Override
		public boolean prompt(String toolToLaunchName) {
			try {
				boolean launchCompare = true;
				outw.flush();
				BufferedReader br = new BufferedReader(new InputStreamReader(ins));
				String line = null;
				if ((line = br.readLine()) != null) {
						launchCompare = false;
					}
				}
				return launchCompare;
			} catch (IOException e) {
				throw new IllegalStateException("Cannot output text"
			}
		}
	}

	private void compare(List<DiffEntry> files) throws IOException {
		ContentSource.Pair sourcePair = new ContentSource.Pair(source(oldTree)
				source(newTree));
		try {
			for (int fileIndex = 0; fileIndex < files.size(); fileIndex++) {
				DiffEntry ent = files.get(fileIndex);

				String filePath = ent.getNewPath();
				if (filePath.equals(DiffEntry.DEV_NULL)) {
					filePath = ent.getOldPath();
				}

				try {
					FileElement local = createFileElement(
							FileElement.Type.LOCAL
					FileElement remote = createFileElement(
							FileElement.Type.REMOTE

					PromptContinueHandler promptContinueHandler = new CountingPromptContinueHandler(
							fileIndex + 1

					Optional<ExecutionResult> optionalResult = diffToolMgr
							.compare(local
									trustExitCode
									this::informUserNoTool);

					if (optionalResult.isPresent()) {
						ExecutionResult result = optionalResult.get();
						outw.println(
								new String(result.getStdout().toByteArray()));
						outw.flush();
						errw.println(
								new String(result.getStderr().toByteArray()));
						errw.flush();
					}
				} catch (ToolException e) {
					outw.println(e.getResultStdout());
					outw.flush();
					errw.println(e.getMessage());
					errw.flush();
					throw die("external diff died
							+ filePath
				}
			}
		} finally {
			sourcePair.close();
		}
	}

	private void showToolHelp() throws IOException {
		outw.println(
		Map<String
				.getPredefinedTools(true);
		for (String name : predefTools.keySet()) {
			if (predefTools.get(name).isAvailable()) {
			}
		}
		Map<String
		for (String name : userTools.keySet()) {
					+ userTools.get(name).getCommand());
		}
		outw.println(
				"The following tools are valid
		for (String name : predefTools.keySet()) {
			if (!predefTools.get(name).isAvailable()) {
			}
		}
		outw.println(
				"environment. If run in a terminal-only session
		outw.flush();
		return;
	}

	private List<DiffEntry> getFiles()
			throws RevisionSyntaxException
			IncorrectObjectTypeException
		diffFmt.setRepository(db);
		if (cached) {
			if (oldTree == null) {
				if (head == null) {
					die(MessageFormat.format(CLIText.get().notATree
				}
				CanonicalTreeParser p = new CanonicalTreeParser();
				try (ObjectReader reader = db.newObjectReader()) {
					p.reset(reader
				}
				oldTree = p;
			}
			newTree = new DirCacheIterator(db.readDirCache());
		} else if (oldTree == null) {
			oldTree = new DirCacheIterator(db.readDirCache());
			newTree = new FileTreeIterator(db);
		} else if (newTree == null) {
			newTree = new FileTreeIterator(db);
		}

		TextProgressMonitor pm = new TextProgressMonitor(errw);
		pm.setDelayStart(2
		diffFmt.setProgressMonitor(pm);
		diffFmt.setPathFilter(pathFilter);

		List<DiffEntry> files = diffFmt.scan(oldTree
		return files;
	}

	private FileElement createFileElement(FileElement.Type elementType
			Pair pair
			throws NoWorkTreeException
			ToolException {
		String entryPath = side == Side.NEW ? entry.getNewPath()
				: entry.getOldPath();
		FileElement fileElement = new FileElement(entryPath
				db.getWorkTree());
		if (!pair.isWorkingTreeSource(side) && !fileElement.isNullPath()) {
			try (RevWalk revWalk = new RevWalk(db);
					TreeWalk treeWalk = new TreeWalk(db
							revWalk.getObjectReader())) {
				treeWalk.setFilter(
						PathFilterGroup.createFromStrings(entryPath));
				if (side == Side.NEW) {
					newTree.reset();
					treeWalk.addTree(newTree);
				} else {
					oldTree.reset();
					treeWalk.addTree(oldTree);
				}
				if (treeWalk.next()) {
					final EolStreamType eolStreamType = treeWalk
							.getEolStreamType(CHECKOUT_OP);
					final String filterCommand = treeWalk.getFilterCommand(
							Constants.ATTR_FILTER_TYPE_SMUDGE);
					WorkingTreeOptions opt = db.getConfig()
							.get(WorkingTreeOptions.KEY);
					CheckoutMetadata checkoutMetadata = new CheckoutMetadata(
							eolStreamType
					DirCacheCheckout.checkoutToFile(db
							checkoutMetadata
							db.getFS()
				} else {
							+ "' in staging area!"
				}
			}
		}
		return fileElement;
	}

	private ContentSource source(AbstractTreeIterator iterator) {
		if (iterator instanceof WorkingTreeIterator)
			return ContentSource.create((WorkingTreeIterator) iterator);
		return ContentSource.create(db.newObjectReader());
	}

}
