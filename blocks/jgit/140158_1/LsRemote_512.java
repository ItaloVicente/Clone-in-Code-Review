package org.eclipse.jgit.pgm;

import static java.util.function.Predicate.isEqual;
import static org.eclipse.jgit.lib.FileMode.EXECUTABLE_FILE;
import static org.eclipse.jgit.lib.FileMode.GITLINK;
import static org.eclipse.jgit.lib.FileMode.REGULAR_FILE;
import static org.eclipse.jgit.lib.FileMode.SYMLINK;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;
import org.eclipse.jgit.util.QuotedString;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.StopOptionHandler;

@Command(common = true
class LsFiles extends TextBuiltin {

	@Option(name = "--"
	private List<String> paths = new ArrayList<>();

	@Override
	protected void run() {
		try (RevWalk rw = new RevWalk(db);
				TreeWalk tw = new TreeWalk(db)) {
			final ObjectId head = db.resolve(Constants.HEAD);
			if (head == null) {
				return;
			}
			RevCommit c = rw.parseCommit(head);
			CanonicalTreeParser p = new CanonicalTreeParser();
			p.reset(rw.getObjectReader()
			if (paths.size() > 0) {
				tw.setFilter(PathFilterGroup.createFromStrings(paths));
			}
			tw.addTree(p);
			tw.addTree(new DirCacheIterator(db.readDirCache()));
			tw.setRecursive(true);
			while (tw.next()) {
				if (filterFileMode(tw
						SYMLINK)) {
					outw.println(
							QuotedString.GIT_PATH.quote(tw.getPathString()));
				}
			}
		} catch (RevisionSyntaxException | IOException e) {
			throw die(e.getMessage()
		}
	}

	private boolean filterFileMode(TreeWalk tw
		return Arrays.stream(modes).anyMatch(isEqual(tw.getFileMode(0))
				.or(isEqual(tw.getFileMode(1))));
	}
}
