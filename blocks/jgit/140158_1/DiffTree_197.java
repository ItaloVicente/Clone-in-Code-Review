
package org.eclipse.jgit.pgm;

import static java.lang.Integer.valueOf;
import static org.eclipse.jgit.lib.Constants.HEAD;
import static org.eclipse.jgit.lib.Constants.OBJECT_ID_STRING_LENGTH;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.eclipse.jgit.diff.DiffAlgorithm;
import org.eclipse.jgit.diff.DiffAlgorithm.SupportedAlgorithm;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.diff.RenameDetector;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.eclipse.jgit.pgm.opt.PathTreeFilterHandler;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.eclipse.jgit.util.io.ThrowingPrintWriter;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

@Command(common = true
class Diff extends TextBuiltin {
	private DiffFormatter diffFmt;

	@Argument(index = 0
	private AbstractTreeIterator oldTree;

	@Argument(index = 1
	private AbstractTreeIterator newTree;

	@Option(name = "--cached"
	private boolean cached;

	@Option(name = "--"
	private TreeFilter pathFilter = TreeFilter.ALL;

	@Option(name = "-p"
	boolean showPatch;

	@Option(name = "-M"
	private Boolean detectRenames;

	@Option(name = "--no-renames"
	void noRenames(@SuppressWarnings("unused") boolean on) {
		detectRenames = Boolean.FALSE;
	}

	@Option(name = "--algorithm"
	void setAlgorithm(SupportedAlgorithm s) {
		diffFmt.setDiffAlgorithm(DiffAlgorithm.getAlgorithm(s));
	}

	@Option(name = "-l"
	private Integer renameLimit;

	@Option(name = "--name-status"
	private boolean showNameAndStatusOnly;

	@Option(name = "--ignore-space-at-eol")
	void ignoreSpaceAtEol(@SuppressWarnings("unused") boolean on) {
		diffFmt.setDiffComparator(RawTextComparator.WS_IGNORE_TRAILING);
	}

	@Option(name = "--ignore-leading-space")
	void ignoreLeadingSpace(@SuppressWarnings("unused") boolean on) {
		diffFmt.setDiffComparator(RawTextComparator.WS_IGNORE_LEADING);
	}

	@Option(name = "-b"
	void ignoreSpaceChange(@SuppressWarnings("unused") boolean on) {
		diffFmt.setDiffComparator(RawTextComparator.WS_IGNORE_CHANGE);
	}

	@Option(name = "-w"
	void ignoreAllSpace(@SuppressWarnings("unused") boolean on) {
		diffFmt.setDiffComparator(RawTextComparator.WS_IGNORE_ALL);
	}

	@Option(name = "-U"
	void unified(int lines) {
		diffFmt.setContext(lines);
	}

	@Option(name = "--abbrev"
	void abbrev(int lines) {
		diffFmt.setAbbreviationLength(lines);
	}

	@Option(name = "--full-index")
	void abbrev(@SuppressWarnings("unused") boolean on) {
		diffFmt.setAbbreviationLength(OBJECT_ID_STRING_LENGTH);
	}

	@Option(name = "--src-prefix"
	void sourcePrefix(String path) {
		diffFmt.setOldPrefix(path);
	}

	@Option(name = "--dst-prefix"
	void dstPrefix(String path) {
		diffFmt.setNewPrefix(path);
	}

	@Option(name = "--no-prefix"
	void noPrefix(@SuppressWarnings("unused") boolean on) {
	}


	@Override
	protected void init(Repository repository
		super.init(repository
		diffFmt = new DiffFormatter(new BufferedOutputStream(outs));
	}

	@Override
	protected void run() {
		diffFmt.setRepository(db);
		try {
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
			if (detectRenames != null) {
				diffFmt.setDetectRenames(detectRenames.booleanValue());
			}
			if (renameLimit != null && diffFmt.isDetectRenames()) {
				RenameDetector rd = diffFmt.getRenameDetector();
				rd.setRenameLimit(renameLimit.intValue());
			}

			if (showNameAndStatusOnly) {
				nameStatus(outw
				outw.flush();
			} else {
				diffFmt.format(oldTree
				diffFmt.flush();
			}
		} catch (RevisionSyntaxException | IOException e) {
			throw die(e.getMessage()
		} finally {
			diffFmt.close();
		}
	}

	static void nameStatus(ThrowingPrintWriter out
			throws IOException {
		for (DiffEntry ent : files) {
			switch (ent.getChangeType()) {
			case ADD:
				break;
			case DELETE:
				break;
			case MODIFY:
				break;
			case COPY:
				out.format("C%1$03d\t%2$s\t%3$s"
						ent.getOldPath()
				out.println();
				break;
			case RENAME:
				out.format("R%1$03d\t%2$s\t%3$s"
						ent.getOldPath()
				out.println();
				break;
			}
		}
	}
}
