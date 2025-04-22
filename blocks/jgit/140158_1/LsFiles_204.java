
package org.eclipse.jgit.pgm;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.diff.RenameDetector;
import org.eclipse.jgit.errors.LargeObjectException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.notes.NoteMap;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.util.GitDateFormatter;
import org.eclipse.jgit.util.GitDateFormatter.Format;
import org.kohsuke.args4j.Option;

@Command(common = true
class Log extends RevWalkTextBuiltin {

	private GitDateFormatter dateFormatter = new GitDateFormatter(
			Format.DEFAULT);

	private DiffFormatter diffFmt;

	private Map<AnyObjectId

	private Map<String

	@Option(name="--decorate"
	private boolean decorate;

	@Option(name = "--no-standard-notes"
	private boolean noStandardNotes;

	private List<String> additionalNoteRefs = new ArrayList<>();

	@Option(name = "--show-notes"
	void addAdditionalNoteRef(String notesRef) {
		additionalNoteRefs.add(notesRef);
	}

	@Option(name = "--date"
	void dateFormat(String date) {
		if (date.toLowerCase(Locale.ROOT).equals(date))
			date = date.toUpperCase(Locale.ROOT);
		dateFormatter = new GitDateFormatter(Format.valueOf(date));
	}

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



	Log() {
		dateFormatter = new GitDateFormatter(Format.DEFAULT);
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
			diffFmt.setPathFilter(pathFilter);
			if (detectRenames != null) {
				diffFmt.setDetectRenames(detectRenames.booleanValue());
			}
			if (renameLimit != null && diffFmt.isDetectRenames()) {
				RenameDetector rd = diffFmt.getRenameDetector();
				rd.setRenameLimit(renameLimit.intValue());
			}

			if (!noStandardNotes || !additionalNoteRefs.isEmpty()) {
				createWalk();
				noteMaps = new LinkedHashMap<>();
				if (!noStandardNotes) {
					addNoteMap(Constants.R_NOTES_COMMITS);
				}
				if (!additionalNoteRefs.isEmpty()) {
					for (String notesRef : additionalNoteRefs) {
						if (!notesRef.startsWith(Constants.R_NOTES)) {
							notesRef = Constants.R_NOTES + notesRef;
						}
						addNoteMap(notesRef);
					}
				}
			}

			if (decorate) {
				allRefsByPeeledObjectId = getRepository()
						.getAllRefsByPeeledObjectId();
			}
			super.run();
		} catch (Exception e) {
			throw die(e.getMessage()
		} finally {
			diffFmt.close();
		}
	}

	private void addNoteMap(String notesRef) throws IOException {
		Ref notes = db.exactRef(notesRef);
		if (notes == null)
			return;
		RevCommit notesCommit = argWalk.parseCommit(notes.getObjectId());
		noteMaps.put(notesRef
				NoteMap.read(argWalk.getObjectReader()
	}

	@Override
	protected void show(RevCommit c) throws Exception {
		outw.print(CLIText.get().commitLabel);
		c.getId().copyTo(outbuffer
		if (decorate) {
			Collection<Ref> list = allRefsByPeeledObjectId.get(c);
			if (list != null) {
				for (Iterator<Ref> i = list.iterator(); i.hasNext(); ) {
					outw.print(i.next().getName());
					if (i.hasNext())
				}
			}
		}
		outw.println();

		final PersonIdent author = c.getAuthorIdent();
		outw.println(MessageFormat.format(CLIText.get().authorInfo
		outw.println(MessageFormat.format(CLIText.get().dateInfo
				dateFormatter.formatDate(author)));

		outw.println();
		for (String s : lines) {
			outw.print(s);
			outw.println();
		}
		c.disposeBody();

		outw.println();
		if (showNotes(c))
			outw.println();

		if (c.getParentCount() <= 1 && (showNameAndStatusOnly || showPatch))
			showDiff(c);
		outw.flush();
	}

	private boolean showNotes(RevCommit c) throws IOException {
		if (noteMaps == null)
			return false;

		boolean printEmptyLine = false;
		boolean atLeastOnePrinted = false;
		for (Map.Entry<String
			String label = null;
			String notesRef = e.getKey();
			if (! notesRef.equals(Constants.R_NOTES_COMMITS)) {
				if (notesRef.startsWith(Constants.R_NOTES))
					label = notesRef.substring(Constants.R_NOTES.length());
				else
					label = notesRef;
			}
			boolean printedNote = showNotes(c
					printEmptyLine);
			atLeastOnePrinted |= printedNote;
			printEmptyLine = printedNote;
		}
		return atLeastOnePrinted;
	}

	private boolean showNotes(RevCommit c
			boolean emptyLine)
			throws IOException {
		ObjectId blobId = map.get(c);
		if (blobId == null)
			return false;
		if (emptyLine)
			outw.println();
		if (label != null) {
			outw.print(label);
		}
		try {
			RawText rawText = new RawText(argWalk.getObjectReader()
					.open(blobId).getCachedBytes(Integer.MAX_VALUE));
			for (int i = 0; i < rawText.size(); i++) {
				outw.println(rawText.getString(i));
			}
		} catch (LargeObjectException e) {
			outw.println(MessageFormat.format(
					CLIText.get().noteObjectTooLargeToPrint
		}
		return true;
	}

	private void showDiff(RevCommit c) throws IOException {
		final RevTree a = c.getParentCount() > 0 ? c.getParent(0).getTree()
				: null;
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
