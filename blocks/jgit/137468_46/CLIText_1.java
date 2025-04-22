
package org.eclipse.jgit.pgm;

import static org.eclipse.jgit.lib.Constants.HEAD;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.internal.diffmergetool.DiffTools;
import org.eclipse.jgit.internal.diffmergetool.ExternalDiffTool;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.eclipse.jgit.lib.internal.BooleanTriState;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.eclipse.jgit.pgm.opt.PathTreeFilterHandler;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.eclipse.jgit.util.StringUtils;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

@Command(name = "difftool"
class DiffTool extends TextBuiltin {
	private DiffFormatter diffFmt;

	private DiffTools diffTools;

	@Argument(index = 0
	private AbstractTreeIterator oldTree;

	@Argument(index = 1
	private AbstractTreeIterator newTree;

	@Option(name = "--tool"
			"-t" }
	private String toolName;

	@Option(name = "--cached"
	private boolean cached;

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

	private BooleanTriState trustExitCode = BooleanTriState.UNSET;

	@Option(name = "--trust-exit-code"
	void setTrustExitCode(@SuppressWarnings("unused") boolean on) {
		trustExitCode = BooleanTriState.TRUE;
	}

	@Option(name = "--no-trust-exit-code"
	void noTrustExitCode(@SuppressWarnings("unused") boolean on) {
		trustExitCode = BooleanTriState.FALSE;
	}

	@Option(name = "--"
	private TreeFilter pathFilter = TreeFilter.ALL;

	@Override
	protected void init(Repository repository
		super.init(repository
		diffFmt = new DiffFormatter(new BufferedOutputStream(outs));
		diffTools = new DiffTools(repository);
	}

	@Override
	protected void run() {
		try {
			if (toolHelp) {
				showToolHelp();
			} else {
				boolean showPrompt = diffTools.isInteractive();
				if (prompt != BooleanTriState.UNSET) {
					showPrompt = prompt == BooleanTriState.TRUE;
				}
				String toolNamePrompt = toolName;
				if (showPrompt) {
					if (StringUtils.isEmptyOrNull(toolNamePrompt)) {
						toolNamePrompt = diffTools.getDefaultToolName(gui);
					}
				}
				List<DiffEntry> files = getFiles();
				if (files.size() > 0) {
					compare(files
				}
			}
			outw.flush();
		} catch (RevisionSyntaxException | IOException e) {
			throw die(e.getMessage()
		} finally {
			diffFmt.close();
		}
	}

	private void compare(List<DiffEntry> files
			String toolNamePrompt) throws IOException {
		for (int fileIndex = 0; fileIndex < files.size(); fileIndex++) {
			DiffEntry ent = files.get(fileIndex);
			String mergedFilePath = ent.getNewPath();
			if (mergedFilePath.equals(DiffEntry.DEV_NULL)) {
				mergedFilePath = ent.getOldPath();
			}
			boolean launchCompare = true;
			if (showPrompt) {
				launchCompare = isLaunchCompare(fileIndex + 1
						mergedFilePath
			}
			if (launchCompare) {
				switch (ent.getChangeType()) {
				case MODIFY:
					int ret = diffTools.compare(ent.getNewPath()
							ent.getOldPath()
							ent.getOldId().name()
							trustExitCode);
					if (ret != 0) {
						throw die(MessageFormat.format(
								CLIText.get().diffToolDied
					}
					break;
				default:
					break;
				}
			} else {
				break;
			}
		}
	}

	@SuppressWarnings("boxing")
	private boolean isLaunchCompare(int fileIndex
			String fileName
		boolean launchCompare = true;
		outw.println(MessageFormat.format(CLIText.get().diffToolLaunch
				fileIndex
		outw.flush();
		BufferedReader br = new BufferedReader(new InputStreamReader(ins));
		String line = null;
		if ((line = br.readLine()) != null) {
				launchCompare = false;
			}
		}
		return launchCompare;
	}

	private void showToolHelp() throws IOException {
		String availableToolNames = new String();
		for (String name : diffTools.getAvailableTools().keySet()) {
			availableToolNames += String.format("\t\t{0}\n"
		}
		String notAvailableToolNames = new String();
		for (String name : diffTools.getNotAvailableTools().keySet()) {
			notAvailableToolNames += String.format("\t\t{0}\n"
		}
		String userToolNames = new String();
		Map<String
				.getUserDefinedTools();
		for (String name : userTools.keySet()) {
			availableToolNames += String.format("\t\t{0}.cmd {1}\n"
					name
		}
		outw.println(MessageFormat.format(
				CLIText.get().diffToolHelpSetToFollowing
				userToolNames
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

}
