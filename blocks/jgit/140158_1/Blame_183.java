
package org.eclipse.jgit.pgm;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.eclipse.jgit.api.ArchiveCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.archive.ArchiveFormats;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

@Command(common = true
class Archive extends TextBuiltin {
	static {
		ArchiveFormats.registerAll();
	}

	@Argument(index = 0
	private ObjectId tree;

	@Option(name = "--format"
	private String format;

	@Option(name = "--prefix"
	private String prefix;

	@Option(name = "--output"
	private String output;

	@Override
	protected void run() throws Exception {
		if (tree == null)
			throw die(CLIText.get().treeIsRequired);

		OutputStream stream = null;
		try {
			if (output != null)
				stream = new FileOutputStream(output);
			else
				stream = outs;

			try (Git git = new Git(db)) {
				ArchiveCommand cmd = git.archive()
					.setTree(tree)
					.setFormat(format)
					.setPrefix(prefix)
					.setOutputStream(stream);
				if (output != null)
					cmd.setFilename(output);
				cmd.call();
			} catch (GitAPIException e) {
				throw die(e.getMessage()
			}
		} catch (FileNotFoundException e) {
			throw die(e.getMessage()
		} finally {
			if (output != null && stream != null)
				stream.close();
		}
	}
}
