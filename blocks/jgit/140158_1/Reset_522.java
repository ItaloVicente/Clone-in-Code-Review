package org.eclipse.jgit.pgm;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.gitrepo.RepoCommand;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

@Command(common = true
class Repo extends TextBuiltin {

	@Option(name = "--base-uri"
	private String uri;

	@Option(name = "--groups"

	@Argument(required = true
	private String path;

	@Override
	protected void run() {
		try {
			new RepoCommand(db)
				.setURI(uri)
				.setPath(path)
				.setGroups(groups)
				.call();
		} catch (GitAPIException e) {
			throw die(e.getMessage()
		}
	}
}
