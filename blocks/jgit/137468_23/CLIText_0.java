
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
import org.eclipse.jgit.diffmergetool.DiffTools;
import org.eclipse.jgit.diffmergetool.ExternalDiffTool;
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

	private BooleanOption prompt = BooleanOption.DEFAULT_FALSE;

	@Option(name = "--prompt"
	void setPrompt(@SuppressWarnings("unused") boolean on) {
		prompt = BooleanOption.TRUE;
	}

	@Option(name = "--no-prompt"
	void noPrompt(@SuppressWarnings("unused") boolean on) {
		prompt = BooleanOption.FALSE;
	}

	@Option(name = "--tool-help"
	private boolean toolHelp;

	private BooleanOption gui = BooleanOption.DEFAULT_FALSE;

	@Option(name = "--gui"
	void setGui(@SuppressWarnings("unused") boolean on) {
		gui = BooleanOption.TRUE;
	}

	@Option(name = "--no-gui"
	void noGui(@SuppressWarnings("unused") boolean on) {
		gui = BooleanOption.FALSE;
	}

	private BooleanOption trustExitCode = BooleanOption.DEFAULT_FALSE;

	@Option(name = "--trust-exit-code"
	void setTrustExitCode(@SuppressWarnings("unused") boolean on) {
		trustExitCode = BooleanOption.TRUE;
	}

	@Option(name = "--no-trust-exit-code"
	void noTrustExitCode(@SuppressWarnings("unused") boolean on) {
		trustExitCode = BooleanOption.FALSE;
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
							userTools.get(name).getCommand());
				}
				outw.println(MessageFormat.format(
						CLIText.get().diffToolHelpSetToFollowing
						availableToolNames
						notAvailableToolNames));
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

					ObjectStream newFileStream = sourcePair.open(Side.NEW
							.openStream();
					showStream(newFileStream);
					ObjectStream oldFileStream = sourcePair.open(Side.OLD
							.openStream();
					showStream(oldFileStream);

					diffTools.compare(ent.getNewPath()
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
		if (iterator instanceof WorkingTreeIterator) {
			return ContentSource.create((WorkingTreeIterator) iterator);
		}
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
