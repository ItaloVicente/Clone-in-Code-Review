
package org.eclipse.jgit.pgm.debug;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.ObjectWritingException;
import org.eclipse.jgit.internal.storage.file.LockFile;
import org.eclipse.jgit.lib.CommitBuilder;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RefWriter;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.eclipse.jgit.pgm.Command;
import org.eclipse.jgit.pgm.TextBuiltin;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.eclipse.jgit.revwalk.RevWalk;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

@Command(usage = "usage_RebuildCommitGraph")
class RebuildCommitGraph extends TextBuiltin {

	@Option(name = REALLY
	boolean really;

	@Argument(index = 0
	File refList;

	@Argument(index = 1
	File graph;

	private final ProgressMonitor pm = new TextProgressMonitor(errw);

	private Map<ObjectId

	@Override
	protected void run() throws Exception {
		if (!really && db.getRefDatabase().hasRefs()) {
			File directory = db.getDirectory();
					: directory.getAbsolutePath();
			errw.println(
				MessageFormat.format(CLIText.get().fatalThisProgramWillDestroyTheRepository
					
			throw die(CLIText.get().needApprovalToDestroyCurrentRepository);
		}
		if (!refList.isFile())
			throw die(MessageFormat.format(CLIText.get().noSuchFile
		if (!graph.isFile())
			throw die(MessageFormat.format(CLIText.get().noSuchFile

		recreateCommitGraph();
		detachHead();
		deleteAllRefs();
		recreateRefs();
	}

	private void recreateCommitGraph() throws IOException {
		final Map<ObjectId
		List<ToRewrite> queue = new ArrayList<>();
		try (RevWalk rw = new RevWalk(db);
				final BufferedReader br = new BufferedReader(
						new InputStreamReader(new FileInputStream(graph)
								UTF_8))) {
			String line;
			while ((line = br.readLine()) != null) {
				final String[] parts = line.split("[ \t]{1
				final ObjectId oldId = ObjectId.fromString(parts[0]);
				try {
					rw.parseCommit(oldId);
					continue;
				} catch (MissingObjectException mue) {
				}

				final long time = Long.parseLong(parts[1]) * 1000L;
				final ObjectId[] parents = new ObjectId[parts.length - 2];
				for (int i = 0; i < parents.length; i++) {
					parents[i] = ObjectId.fromString(parts[2 + i]);
				}

				final ToRewrite t = new ToRewrite(oldId
				toRewrite.put(oldId
				queue.add(t);
			}
		}

		pm.beginTask("Rewriting commits"
		try (ObjectInserter oi = db.newObjectInserter()) {
			final ObjectId emptyTree = oi.insert(Constants.OBJ_TREE
					new byte[] {});
			final PersonIdent me = new PersonIdent("jgit rebuild-commitgraph"
			while (!queue.isEmpty()) {
				final ListIterator<ToRewrite> itr = queue
						.listIterator(queue.size());
				queue = new ArrayList<>();
				REWRITE: while (itr.hasPrevious()) {
					final ToRewrite t = itr.previous();
					final ObjectId[] newParents = new ObjectId[t.oldParents.length];
					for (int k = 0; k < t.oldParents.length; k++) {
						final ToRewrite p = toRewrite.get(t.oldParents[k]);
						if (p != null) {
							if (p.newId == null) {
								queue.add(t);
								continue REWRITE;
							} else {
								newParents[k] = p.newId;
							}
						} else {
							newParents[k] = t.oldParents[k];
						}
					}

					final CommitBuilder newc = new CommitBuilder();
					newc.setTreeId(emptyTree);
					newc.setAuthor(new PersonIdent(me
					newc.setCommitter(newc.getAuthor());
					newc.setParentIds(newParents);
					t.newId = oi.insert(newc);
					rewrites.put(t.oldId
					pm.update(1);
				}
			}
			oi.flush();
		}
		pm.endTask();
	}

	private static class ToRewrite {
		final ObjectId oldId;

		final long commitTime;

		final ObjectId[] oldParents;

		ObjectId newId;

		ToRewrite(ObjectId o
			oldId = o;
			commitTime = t;
			oldParents = p;
		}
	}

	private void detachHead() throws IOException {
		final String head = db.getFullBranch();
		final ObjectId id = db.resolve(Constants.HEAD);
		if (!ObjectId.isId(head) && id != null) {
			final LockFile lf;
			lf = new LockFile(new File(db.getDirectory()
			if (!lf.lock())
				throw new IOException(MessageFormat.format(CLIText.get().cannotLock
			lf.write(id);
			if (!lf.commit())
				throw new IOException(CLIText.get().cannotDeatchHEAD);
		}
	}

	private void deleteAllRefs() throws Exception {
		final RevWalk rw = new RevWalk(db);
		for (Ref r : db.getRefDatabase().getRefs()) {
			if (Constants.HEAD.equals(r.getName()))
				continue;
			final RefUpdate u = db.updateRef(r.getName());
			u.setForceUpdate(true);
			u.delete(rw);
		}
	}

	private void recreateRefs() throws Exception {
		final Map<String
		new RefWriter(refs.values()) {
			@Override
			protected void writeFile(String name
					throws IOException {
				final File file = new File(db.getDirectory()
				final LockFile lck = new LockFile(file);
				if (!lck.lock())
					throw new ObjectWritingException(MessageFormat.format(CLIText.get().cantWrite
				try {
					lck.write(content);
				} catch (IOException ioe) {
					throw new ObjectWritingException(MessageFormat.format(CLIText.get().cantWrite
				}
				if (!lck.commit())
					throw new ObjectWritingException(MessageFormat.format(CLIText.get().cantWrite
			}
		}.writePackedRefs();
	}

	private Map<String
		final Map<String
		try (RevWalk rw = new RevWalk(db);
				BufferedReader br = new BufferedReader(
						new InputStreamReader(new FileInputStream(refList)
								UTF_8))) {
			String line;
			while ((line = br.readLine()) != null) {
				final String[] parts = line.split("[ \t]{1
				final ObjectId origId = ObjectId.fromString(parts[0]);
				final String type = parts[1];
				final String name = parts[2];

				ObjectId id = rewrites.get(origId);
				if (id == null)
					id = origId;
				try {
					rw.parseAny(id);
				} catch (MissingObjectException mue) {
					if (!Constants.TYPE_COMMIT.equals(type)) {
						errw.println(MessageFormat.format(CLIText.get().skippingObject
						continue;
					}
					throw new MissingObjectException(id
				}
				refs.put(name
						name
			}
		}
		return refs;
	}
}
