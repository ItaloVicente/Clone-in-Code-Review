
package org.eclipse.jgit.pgm;

import static org.eclipse.jgit.lib.Constants.HEAD;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.eclipse.jgit.diff.ContentSource;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffEntry.Side;
import org.eclipse.jgit.diffmergetool.BooleanOption;
import org.eclipse.jgit.diffmergetool.DiffToolManager;
import org.eclipse.jgit.diffmergetool.ITool;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.ObjectStream;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.eclipse.jgit.pgm.opt.PathTreeFilterHandler;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.WorkingTreeIterator;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
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

	@Option(name = "--tool"
			"-t" }
	private String toolName;

	@Option(name = "--cached"
	private boolean cached;

	private BooleanOption prompt = BooleanOption.notDefinedFalse;

	@Option(name = "--prompt"
	void setPrompt(@SuppressWarnings("unused") boolean on) {
		prompt = BooleanOption.True;
	}

	@Option(name = "--no-prompt"
	void noPrompt(@SuppressWarnings("unused") boolean on) {
		prompt = BooleanOption.False;
	}

	@Option(name = "--tool-help"
	private boolean toolHelp;

	private BooleanOption gui = BooleanOption.notDefinedFalse;

	@Option(name = "--gui"
	void setGui(@SuppressWarnings("unused") boolean on) {
		gui = BooleanOption.True;
	}

	@Option(name = "--no-gui"
	void noGui(@SuppressWarnings("unused") boolean on) {
		gui = BooleanOption.False;
	}

	private BooleanOption trustExitCode = BooleanOption.notDefinedFalse;

	@Option(name = "--trust-exit-code"
	void setTrustExitCode(@SuppressWarnings("unused") boolean on) {
		trustExitCode = BooleanOption.True;
	}

	@Option(name = "--no-trust-exit-code"
	void noTrustExitCode(@SuppressWarnings("unused") boolean on) {
		trustExitCode = BooleanOption.False;
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
				outw.println(
				for (String name : diffToolMgr.getAvailableTools().keySet()) {
				}
				Map<String
						.getUserDefinedTools();
				for (String name : userTools.keySet()) {
							+ userTools.get(name).getCommand());
				}
				outw.println(
						"The following tools are valid
				for (String name : diffToolMgr.getNotAvailableTools()
						.keySet()) {
				}
				outw.println(
						"environment. If run in a terminal-only session
				return;
			}
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
			ContentSource.Pair sourcePair = new ContentSource.Pair(
					source(oldTree)

			for (DiffEntry ent : files) {
				switch (ent.getChangeType()) {
				case MODIFY:
					outw.println("M\t" + ent.getNewPath()
							+ " (" + ent.getNewId().name() + ")"
							+ "\t" + ent.getOldPath()
							+ " (" + ent.getOldId().name() + ")");
					outw.println("--- NEW-DATA ---");

					ObjectStream newFileStream = sourcePair.open(Side.NEW
							.openStream();
					showStream(newFileStream);
					outw.println("--- OLD-DATA ---");
					ObjectStream oldFileStream = sourcePair.open(Side.OLD
							.openStream();
					showStream(oldFileStream);

					diffToolMgr.compare(ent.getNewPath()
							ent.getOldPath()
							ent.getOldId().name()
							trustExitCode);
					break;
				default:
					break;
				}
			}

			outw.flush();
		} catch (RevisionSyntaxException | IOException e) {
			throw die(e.getMessage()
		} finally {
			diffFmt.close();
		}

	}

	private ContentSource source(AbstractTreeIterator iterator) {
		if (iterator instanceof WorkingTreeIterator)
			return ContentSource.create((WorkingTreeIterator) iterator);
		return ContentSource.create(db.newObjectReader());
	}

	private void showStream(ObjectStream stream)
			throws UnsupportedEncodingException
		int read = 0;
		byte[] bytes = new byte[1024];
		while ((read = stream.read(bytes)) != -1) {
			outw.write(new String(bytes
		}
	}
}
