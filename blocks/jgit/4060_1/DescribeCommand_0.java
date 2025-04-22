
package org.eclipse.jgit.pgm;

import org.eclipse.jgit.api.DescribeCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

class Describe extends TextBuiltin {
	@Option(name = "--abbrev"
	private Integer abbrev;

	@Argument(index = 0
	private String revstr;

	@Override
	protected void run() throws Exception {
		Repository r = new RepositoryBuilder().readEnvironment()
				.findGitDir()
				.build();

		Git g = new Git(r);

		DescribeCommand dc = g.describe();
		if (revstr != null) {
			dc.setObjectId(db.resolve(revstr));
		} else {
			dc.setObjectId(db.resolve("HEAD"));

		}
		if (abbrev != null) {
			dc.setAbbrev(abbrev);
		}
		out.print(dc.call());
		out.println();
	}
}
