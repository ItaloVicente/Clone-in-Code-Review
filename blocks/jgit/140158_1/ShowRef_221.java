
package org.eclipse.jgit.pgm;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.diff.RenameDetector;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.eclipse.jgit.pgm.opt.PathTreeFilterHandler;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

@Command(common = true
class Show extends TextBuiltin {
	private final TimeZone myTZ = TimeZone.getDefault();

	private final DateFormat fmt;

	private DiffFormatter diffFmt;

	@Argument(index = 0
	private String objectName;

	@Option(name = "--"
	protected TreeFilter pathFilter = TreeFilter.ALL;

	@Option(name = "-p"
	boolean showPatch;

	@Option(name = "-M"
	private Boolean detectRenames;

	@Option(name = "--no-renames"
	void noRenames(@SuppressWarnings("unused") boolean on) {
		detectRenames = Boolean.FALSE;
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
		diffFmt.setAbbreviationLength(Constants.OBJECT_ID_STRING_LENGTH);
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


	Show() {
		fmt = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy ZZZZZ"
	}

	@Override
	protected void init(Repository repository
		super.init(repository
		diffFmt = new DiffFormatter(new BufferedOutputStream(outs));
	}

	@SuppressWarnings("boxing")
	@Override
	protected void run() {
		diffFmt.setRepository(db);
		try {
			diffFmt.setPathFilter(pathFilter);
			if (detectRenames != null) {
				diffFmt.setDetectRenames(detectRenames.booleanValue());
			}
			if (renameLimit != null && diffFmt.isDetectRenames()) {
				RenameDetector rd = diffFmt.getRenameDetector();
				rd.setRenameLimit(renameLimit.intValue());
			}

			ObjectId objectId;
			if (objectName == null) {
				objectId = db.resolve(Constants.HEAD);
			} else {
				objectId = db.resolve(objectName);
			}

			try (RevWalk rw = new RevWalk(db)) {
				RevObject obj = rw.parseAny(objectId);
				while (obj instanceof RevTag) {
					show((RevTag) obj);
					obj = ((RevTag) obj).getObject();
					rw.parseBody(obj);
				}

				switch (obj.getType()) {
				case Constants.OBJ_COMMIT:
					show(rw
					break;

				case Constants.OBJ_TREE:
					outw.print(objectName);
					outw.println();
					outw.println();
					show((RevTree) obj);
					break;

				case Constants.OBJ_BLOB:
					db.open(obj
					outw.flush();
					break;

				default:
					throw die(MessageFormat.format(
							CLIText.get().cannotReadBecause
							obj.getType()));
				}
			}
		} catch (RevisionSyntaxException | IOException e) {
			throw die(e.getMessage()
		} finally {
			diffFmt.close();
		}
	}

	private void show(RevTag tag) throws IOException {
		outw.print(CLIText.get().tagLabel);
		outw.print(tag.getTagName());
		outw.println();

		final PersonIdent tagger = tag.getTaggerIdent();
		if (tagger != null) {
			outw.println(MessageFormat.format(CLIText.get().taggerInfo
					tagger.getName()

			final TimeZone taggerTZ = tagger.getTimeZone();
			fmt.setTimeZone(taggerTZ != null ? taggerTZ : myTZ);
			outw.println(MessageFormat.format(CLIText.get().dateInfo
					fmt.format(tagger.getWhen())));
		}

		outw.println();
		for (String s : lines) {
			outw.print(s);
			outw.println();
		}

		outw.println();
	}

	private void show(RevTree obj) throws MissingObjectException
			IncorrectObjectTypeException
		try (TreeWalk walk = new TreeWalk(db)) {
			walk.reset();
			walk.addTree(obj);

			while (walk.next()) {
				outw.print(walk.getPathString());
				final FileMode mode = walk.getFileMode(0);
				if (mode == FileMode.TREE)
				outw.println();
			}
		}
	}

	private void show(RevWalk rw
		char[] outbuffer = new char[Constants.OBJECT_ID_LENGTH * 2];

		outw.print(CLIText.get().commitLabel);
		c.getId().copyTo(outbuffer
		outw.println();

		final PersonIdent author = c.getAuthorIdent();
		outw.println(MessageFormat.format(CLIText.get().authorInfo
				author.getName()

		final TimeZone authorTZ = author.getTimeZone();
		fmt.setTimeZone(authorTZ != null ? authorTZ : myTZ);
		outw.println(MessageFormat.format(CLIText.get().dateInfo
				fmt.format(author.getWhen())));

		outw.println();
		for (String s : lines) {
			outw.print(s);
			outw.println();
		}

		outw.println();
		if (c.getParentCount() == 1) {
			rw.parseHeaders(c.getParent(0));
			showDiff(c);
		}
		outw.flush();
	}

	private void showDiff(RevCommit c) throws IOException {
		final RevTree a = c.getParent(0).getTree();
		final RevTree b = c.getTree();

		if (showNameAndStatusOnly)
			Diff.nameStatus(outw
		else {
			outw.flush();
			diffFmt.format(a
			diffFmt.flush();
		}
		outw.println();
	}
}
