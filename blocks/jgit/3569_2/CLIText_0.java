
package org.eclipse.jgit.pgm;

import static org.eclipse.jgit.lib.Constants.OBJECT_ID_STRING_LENGTH;

import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.eclipse.jgit.blame.BlameGenerator;
import org.eclipse.jgit.blame.BlameResult;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.revwalk.RevCommit;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

@Command(common = false
class Blame extends TextBuiltin {
	private RawTextComparator comparator = RawTextComparator.DEFAULT;

	@Option(name = "-w"
	void ignoreAllSpace(@SuppressWarnings("unused") boolean on) {
		comparator = RawTextComparator.WS_IGNORE_ALL;
	}

	@Option(name = "--abbrev"
	private int abbrev = 7;

	@Option(name = "-l"
	private boolean showLongRevision;

	@Option(name = "-t"
	private boolean showRawTimestamp;

	@Option(name = "-b"
	private boolean showBlankBoundary;

	@Option(name = "--root"
	private boolean root;

	@Option(name = "-L"
	private String rangeString;

	@Argument(index = 0
	private String revision;

	@Argument(index = 1
	private String file;

	private ObjectReader reader;

	private final Map<RevCommit

	private SimpleDateFormat dateFmt;

	private int begin;

	private int end;

	private BlameResult blame;

	@Override
	protected void run() throws Exception {
		if (file == null) {
			if (revision == null)
				throw die(CLIText.get().fileIsRequired);
			file = revision;
			revision = null;
		}

		if (!showBlankBoundary)
			root = db.getConfig().getBoolean("blame"
		if (!root)
			root = db.getConfig().getBoolean("blame"

		if (showRawTimestamp)
			dateFmt = new SimpleDateFormat("ZZZZ");
		else
			dateFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZ");

		BlameGenerator generator = new BlameGenerator(db
		reader = db.newObjectReader();
		try {
			generator.setTextComparator(comparator);
			if (revision != null)
				generator.setStartRevision(db.resolve(revision + "^{commit}"));

			blame = BlameResult.create(generator);
			begin = 0;
			end = blame.getResultContents().size();
			if (rangeString != null)
				parseRangeString();
			blame.computeRange(begin

			int authorWidth = 8;
			int dateWidth = 8;
			for (int line = begin; line < end; line++) {
				authorWidth = Math.max(authorWidth
				dateWidth = Math.max(dateWidth
			}

			int lineWidth = 1 + (int) Math.log10(end + 1);
			String fmt = MessageFormat.format(
				" (%-{0}s %{1}s %{2}d) "
				authorWidth
				dateWidth
				lineWidth);

			for (int line = begin; line < end; line++) {
				out.print(abbreviate(blame.getSourceCommit(line)));
				out.format(fmt
				out.flush();
				blame.getResultContents().writeLine(System.out
				out.print('\n');
			}
		} finally {
			generator.release();
			reader.release();
		}
	}

	private void parseRangeString() {
		String beginStr
		if (rangeString.startsWith("/")) {
			int c = rangeString.indexOf("/
				endStr = rangeString.substring(1);
			} else {
				beginStr = rangeString.substring(0
				endStr = rangeString.substring(c + 1);
			}
		}

		if (beginStr.startsWith("/")) {
			begin = findLine(0
		} else {
			begin = Integer.parseInt(beginStr);
		}

		if (endStr.startsWith("/")) {
			end = findLine(begin
		} else if (endStr.startsWith("-")) {
			end = begin + Integer.parseInt(endStr);
		} else if (endStr.startsWith("+")) {
			end = begin + Integer.parseInt(endStr.substring(1));
		} else {
			end = Integer.parseInt(endStr);
		}
	}

	private int findLine(int b
		String re = regex.substring(1
		Pattern p = Pattern.compile(".*" + re + ".*");
		RawText text = blame.getResultContents();
		for (int line = b; line < text.size(); line++) {
			if (p.matcher(text.getString(line)).matches())
				return line;
		}
		return b;
	}

	private String author(int line) {
		PersonIdent author = blame.getSourceAuthor(line);
		if (author == null)
			return "";
		String name = author.getName();
		return name != null ? name : "";
	}

	private String date(int line) {
		PersonIdent author = blame.getSourceAuthor(line);
		if (author == null)
			return "";

		dateFmt.setTimeZone(author.getTimeZone());
		if (!showRawTimestamp)
			return dateFmt.format(author.getWhen());
		return String.format("%d %s"
				dateFmt.format(author.getWhen()));
	}

	private String abbreviate(RevCommit commit) throws IOException {
		String r = abbreviatedCommits.get(commit);
		if (r != null)
			return r;

		if (showBlankBoundary && commit.getParentCount() == 0)
			commit = null;

		if (commit == null) {
			int len = showLongRevision ? OBJECT_ID_STRING_LENGTH : (abbrev + 1);
			StringBuilder b = new StringBuilder(len);
			for (int i = 0; i < len; i++)
				b.append(' ');
			r = b.toString();

		} else if (!root && commit.getParentCount() == 0) {
			if (showLongRevision)
				r = "^" + commit.name().substring(0
			else
				r = "^" + reader.abbreviate(commit
		} else {
			if (showLongRevision)
				r = commit.name();
			else
				r = reader.abbreviate(commit
		}

		abbreviatedCommits.put(commit
		return r;
	}
}
