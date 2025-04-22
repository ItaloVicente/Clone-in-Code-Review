
package org.eclipse.jgit.pgm;

import static org.eclipse.jgit.lib.Constants.OBJECT_ID_STRING_LENGTH;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.eclipse.jgit.blame.BlameGenerator;
import org.eclipse.jgit.blame.BlameResult;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevFlag;
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
	private int abbrev;

	@Option(name = "-l"
	private boolean showLongRevision;

	@Option(name = "-t"
	private boolean showRawTimestamp;

	@Option(name = "-b"
	private boolean showBlankBoundary;

	@Option(name = "-s"
	private boolean noAuthor;

	@Option(name = "--show-email"
	private boolean showAuthorEmail;

	@Option(name = "--show-name"
	private boolean showSourcePath;

	@Option(name = "--show-number"
	private boolean showSourceLine;

	@Option(name = "--root"
	private boolean root;

	@Option(name = "-L"
	private String rangeString;

	@Option(name = "--reverse"
	private List<RevCommit> reverseRange = new ArrayList<RevCommit>(2);

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

		if (abbrev == 0)
			abbrev = db.getConfig().getInt("core"
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

			if (!reverseRange.isEmpty()) {
				RevCommit rangeStart = null;
				List<RevCommit> rangeEnd = new ArrayList<RevCommit>(2);
				for (RevCommit c : reverseRange) {
					if (c.has(RevFlag.UNINTERESTING))
						rangeStart = c;
					else
						rangeEnd.add(c);
				}
				generator.reverse(rangeStart
			} else if (revision != null) {
				generator.push(null
			} else {
				generator.push(null
				if (!db.isBare()) {
					DirCache dc = db.readDirCache();
					int entry = dc.findEntry(file);
					if (0 <= entry)
						generator.push(null

					File inTree = new File(db.getWorkTree()
					if (inTree.isFile())
						generator.push(null
				}
			}

			blame = BlameResult.create(generator);
			begin = 0;
			end = blame.getResultContents().size();
			if (rangeString != null)
				parseLineRangeOption();
			blame.computeRange(begin

			int authorWidth = 8;
			int dateWidth = 8;
			int pathWidth = 1;
			int maxSourceLine = 1;
			for (int line = begin; line < end; line++) {
				authorWidth = Math.max(authorWidth
				dateWidth = Math.max(dateWidth
				pathWidth = Math.max(pathWidth
				maxSourceLine = Math.max(maxSourceLine
			}

			String pathFmt = MessageFormat.format(" %{0}s"
			String numFmt = MessageFormat.format(" %{0}d"
					1 + (int) Math.log10(maxSourceLine + 1));
			String lineFmt = MessageFormat.format(" %{0}d) "
					1 + (int) Math.log10(end + 1));
			String authorFmt = MessageFormat.format(" (%-{0}s %{1}s"
					authorWidth

			for (int line = begin; line < end; line++) {
				out.print(abbreviate(blame.getSourceCommit(line)));
				if (showSourcePath)
					out.format(pathFmt
				if (showSourceLine)
					out.format(numFmt
				if (!noAuthor)
					out.format(authorFmt
				out.format(lineFmt
				out.flush();
				blame.getResultContents().writeLine(System.out
				out.print('\n');
			}
		} finally {
			generator.release();
			reader.release();
		}
	}

	private void parseLineRangeOption() {
		String beginStr
		if (rangeString.startsWith("/")) {
			int c = rangeString.indexOf("/
				endStr = rangeString.substring(1);
			} else {
				beginStr = rangeString.substring(0
				endStr = rangeString.substring(c + 1);
			}
		}

		if (beginStr.equals(""))
			begin = 0;
		else if (beginStr.startsWith("/"))
			begin = findLine(0
		else
			begin = Math.max(0

		if (endStr.equals(""))
			end = blame.getResultContents().size();
		else if (endStr.startsWith("/"))
			end = findLine(begin
		else if (endStr.startsWith("-"))
			end = begin + Integer.parseInt(endStr);
		else if (endStr.startsWith("+"))
			end = begin + Integer.parseInt(endStr.substring(1));
		else
			end = Math.max(0
	}

	private int findLine(int b
		String re = regex.substring(1
		if (!re.startsWith("^"))
			re = ".*" + re;
		if (!re.endsWith("$"))
			re = re + ".*";
		Pattern p = Pattern.compile(re);
		RawText text = blame.getResultContents();
		for (int line = b; line < text.size(); line++) {
			if (p.matcher(text.getString(line)).matches())
				return line;
		}
		return b;
	}

	private String path(int line) {
		String p = blame.getSourcePath(line);
		return p != null ? p : "";
	}

	private String author(int line) {
		PersonIdent author = blame.getSourceAuthor(line);
		if (author == null)
			return "";
		String name = showAuthorEmail ? author.getEmailAddress() : author
				.getName();
		return name != null ? name : "";
	}

	private String date(int line) {
		if (blame.getSourceCommit(line) == null)
			return "";

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
