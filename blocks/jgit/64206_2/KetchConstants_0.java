
package org.eclipse.jgit.internal.ketch;

import static org.eclipse.jgit.internal.ketch.KetchConstants.TERM;

import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.lib.CommitBuilder;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.TreeFormatter;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ElectionRound extends Round {
	private static final Logger log = LoggerFactory.getLogger(ElectionRound.class);

	private long term;

	ElectionRound(KetchLeader leader
		super(leader
	}

	@Override
	void start() throws IOException {
		ObjectId id;
		try (Repository git = leader.openRepository();
				ObjectInserter inserter = git.newObjectInserter()) {
			id = bumpTerm(git
			inserter.flush();
		}
		acceptAsync(id);
	}

	@Override
	void success() {
	}

	long getTerm() {
		return term;
	}

	private ObjectId bumpTerm(Repository git
			throws IOException {
		long newTerm;
		CommitBuilder b = new CommitBuilder();
		if (!ObjectId.zeroId().equals(acceptedOld)) {
			try (RevWalk rw = new RevWalk(git)) {
				RevCommit c = rw.parseCommit(acceptedOld);
				b.setTreeId(c.getTree());
				b.setParentId(acceptedOld);
				newTerm = parseTerm(c.getFooterLines(TERM)) + 1;
			}
		} else {
			newTerm = 1;
			b.setTreeId(inserter.insert(new TreeFormatter()));
		}

		StringBuilder m = new StringBuilder();
		m.append(KetchConstants.TERM.getName())
		 .append(newTerm);

		String tag = leader.getSystem().newLeaderTag();
		if (tag != null && !tag.isEmpty()) {
			m.append(' ').append(tag);
		}

		b.setAuthor(leader.getSystem().newCommitter());
		b.setCommitter(b.getAuthor());
		b.setMessage(m.toString());

		if (log.isDebugEnabled()) {
		}
		term = newTerm;
		return inserter.insert(b);
	}

	private static long parseTerm(List<String> footer) {
		if (footer.isEmpty()) {
			return 0;
		}

		String s = footer.get(0);
		int p = s.indexOf(' ');
		if (p > 0) {
			s = s.substring(0
		}
		return Long.parseLong(s
	}
}
