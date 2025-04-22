
package org.eclipse.jgit.pgm;

import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.blame.BlameEntry;
import org.eclipse.jgit.blame.Range;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.util.RawParseUtils;
import org.kohsuke.args4j.Argument;

@Command(common = true
class Blame extends TextBuiltin {

	@Argument(metaVar = "metaVar_path"
	String filePath;

	@Override
	protected void run() throws Exception {
		Git git = new Git(db);
		Iterable<BlameEntry> blame = git.blame().setPath(filePath).call();
		String[] lines = getRevisionContent();
		for (BlameEntry blameEntry : blame) {
			Range range = blameEntry.originalRange;
			int start = range.getStart();
			int end = range.getStart() + range.getLength();
			for (int i = start; i < end; i++)
				printLine(lines
		}

	}

	private void printLine(String[] lines
		RevCommit commit = blameEntry.suspect.getCommit();

		int maxLineLength = String.valueOf(lines.length).length();
		String outputFormat = "%s [%-15s] %" + maxLineLength + "d) %s";
		String format = String.format(outputFormat
				commit.getName().substring(0
						.getName()
		System.out.println(format);
	}

	private String[] getRevisionContent() throws AmbiguousObjectException
			IOException
			CorruptObjectException {
		ObjectId headId = db.resolve(Constants.HEAD);
		RevTree headCommit = new RevWalk(db).parseTree(headId);
		TreeWalk walk = TreeWalk.forPath(db
		ObjectReader objectReader = walk.getObjectReader();
		ObjectLoader objectLoader = objectReader.open(walk.getObjectId(0));
		byte[] bytes = objectLoader.getBytes();
		String decode = RawParseUtils.decode(bytes);
		String[] lines = decode.split("\n"
		return lines;
	}

}
