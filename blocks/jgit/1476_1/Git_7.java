package org.eclipse.jgit.api;

import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.blame.BlameEntry;
import org.eclipse.jgit.blame.MyersDiffImpl;
import org.eclipse.jgit.blame.Origin;
import org.eclipse.jgit.blame.Scoreboard;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

public class BlameCommand extends GitCommand<Iterable<BlameEntry>> {

	private final RevWalk revWalk;

	private String path;

	private RevCommit initialCommit;

	public BlameCommand(Repository repo) {
		super(repo);
		this.revWalk = new RevWalk(repo);
	}

	public Iterable<BlameEntry> call() throws NoHeadException {
		checkCallable();
		if (path == null)
			throw new IllegalArgumentException("No path given");

		List<BlameEntry> blame;
		if (initialCommit == null) {
			try {
				ObjectId headId = getRepository().resolve(Constants.HEAD);
				if (headId == null)
					throw new NoHeadException("no head found");
				initialCommit = revWalk.parseCommit(headId);
			} catch (IOException e) {
				throw new JGitInternalException(
						"Exeception during blame command"
			}
		}

		Origin finalOrigin = new Origin(getRepository()
		Scoreboard scoreboard = new Scoreboard(finalOrigin
		blame = scoreboard.assingBlame();
		setCallable(false);
		return blame;
	}

	public BlameCommand setPath(String filepath) {
		checkCallable();
		this.path = filepath;
		return this;
	}

	public BlameCommand setStartCommit(RevCommit startCommit) {
		checkCallable();
		initialCommit = startCommit;
		return this;
	}
}
