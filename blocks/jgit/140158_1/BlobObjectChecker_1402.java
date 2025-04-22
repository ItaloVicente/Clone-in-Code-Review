
package org.eclipse.jgit.lib;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.util.RawParseUtils;

public class BlobBasedConfig extends Config {
	public BlobBasedConfig(Config base
			throws ConfigInvalidException {
		super(base);
		final String decoded;
		if (isUtf8(blob)) {
			decoded = RawParseUtils.decode(UTF_8
		} else {
			decoded = RawParseUtils.decode(blob);
		}
		fromText(decoded);
	}

	public BlobBasedConfig(Config base
			throws IOException
		this(base
	}

	private static byte[] read(Repository db
			throws MissingObjectException
			IOException {
		try (ObjectReader or = db.newObjectReader()) {
			return read(or
		}
	}

	private static byte[] read(ObjectReader or
			throws MissingObjectException
			IOException {
		ObjectLoader loader = or.open(blobId
		return loader.getCachedBytes(Integer.MAX_VALUE);
	}

	public BlobBasedConfig(Config base
			String path) throws FileNotFoundException
			ConfigInvalidException {
		this(base
	}

	private static byte[] read(Repository db
			throws MissingObjectException
			IOException {
		try (ObjectReader or = db.newObjectReader()) {
			TreeWalk tree = TreeWalk.forPath(or
			if (tree == null)
				throw new FileNotFoundException(MessageFormat.format(JGitText
						.get().entryNotFoundByPath
			return read(or
		}
	}

	private static AnyObjectId asTree(ObjectReader or
			throws MissingObjectException
			IOException {
		if (treeish instanceof RevTree)
			return treeish;

		if (treeish instanceof RevCommit
				&& ((RevCommit) treeish).getTree() != null)
			return ((RevCommit) treeish).getTree();

		try (RevWalk rw = new RevWalk(or)) {
			return rw.parseTree(treeish).getId();
		}
	}
}
