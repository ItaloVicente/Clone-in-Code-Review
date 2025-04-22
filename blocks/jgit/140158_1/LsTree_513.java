
package org.eclipse.jgit.pgm;

import java.io.IOException;
import java.util.Comparator;
import java.util.TreeSet;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LsRemoteCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Ref;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

@Command(common = true
class LsRemote extends TextBuiltin {
	@Option(name = "--heads"
	private boolean heads;

	@Option(name = "--tags"
	private boolean tags;

	@Option(name = "--timeout"
	int timeout = -1;

	@Argument(index = 0
	private String remote;

	@Override
	protected void run() {
		LsRemoteCommand command = Git.lsRemoteRepository().setRemote(remote)
				.setTimeout(timeout).setHeads(heads).setTags(tags);
		TreeSet<Ref> refs = new TreeSet<>(new Comparator<Ref>() {

			@Override
			public int compare(Ref r1
				return r1.getName().compareTo(r2.getName());
			}
		});
		try {
			refs.addAll(command.call());
			for (Ref r : refs) {
				show(r.getObjectId()
				if (r.getPeeledObjectId() != null) {
					show(r.getPeeledObjectId()
				}
			}
		} catch (GitAPIException | IOException e) {
			throw die(e.getMessage()
		}
	}

	@Override
	protected boolean requiresRepository() {
		return false;
	}

	private void show(AnyObjectId id
			throws IOException {
		outw.print(id.name());
		outw.print('\t');
		outw.print(name);
		outw.println();
	}
}
