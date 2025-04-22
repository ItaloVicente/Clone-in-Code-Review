
package org.eclipse.jgit.storage.dht;

import static org.eclipse.jgit.lib.Constants.OBJECT_ID_LENGTH;
import static org.eclipse.jgit.lib.Constants.OBJ_BLOB;
import static org.eclipse.jgit.lib.Constants.OBJ_COMMIT;
import static org.eclipse.jgit.lib.Constants.OBJ_TREE;

import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.ObjectWalk;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevSort;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.dht.spi.Database;
import org.eclipse.jgit.storage.dht.spi.WriteBuffer;

public class ObjectListCreator {
	private final DhtRepository repository;

	private final Database db;

	private final RepositoryKey repo;

	private final DhtInserterOptions options;

	private ObjectReader reader;

	private ObjectListInfo listInfo;

	private WriteBuffer dbBuffer;

	public ObjectListCreator(DhtRepository repository) {
		this.repository = repository;
		this.db = repository.getDatabase();
		this.repo = repository.getRepositoryKey();
		this.options = DhtInserterOptions.DEFAULT;
	}

	public void create() throws IOException {
		create(NullProgressMonitor.INSTANCE);
	}

	public void create(ProgressMonitor pm) throws IOException {
		if (pm == null)
			pm = NullProgressMonitor.INSTANCE;
		reader = repository.newObjectReader();
		try {
			RevCommit name = selectListName(pm);
			if (name == null)
				return;

			createAndPutList(name
		} finally {
			reader.release();
		}
	}

	private RevCommit selectListName(ProgressMonitor pm) throws IOException {
		pm.beginTask(DhtText.get().objectListSelectingName
				ProgressMonitor.UNKNOWN);
		try {
			final RevWalk walk = new RevWalk(reader);
			walk.setRetainBody(false);
			walk.sort(RevSort.COMMIT_TIME_DESC);
			for (Ref ref : repository.getAllRefs().values()) {
				if (ref.getObjectId() == null)
					continue;
				try {
					walk.markStart(walk.parseCommit(ref.getObjectId()));
				} catch (MissingObjectException notFound) {
					continue;
				} catch (IncorrectObjectTypeException notCommit) {
					continue;
				}
				pm.update(1);
			}

			final RevCommit first = walk.next();
			if (first == null)
				return null;

			final int mostRecent = first.getCommitTime();
			int commitsToSkip = options.getObjectListCommitsToSkip();
			int ageToSkip = options.getObjectListSecondsToSkip();

			RevCommit last = null;
			while ((last = walk.next()) != null) {
				pm.update(1);
				if (--commitsToSkip == 0)
					return last;
				if (mostRecent - last.getCommitTime() > ageToSkip)
					return last;
			}
			return last;
		} finally {
			pm.endTask();
		}
	}

	private void createAndPutList(RevCommit name
			throws IOException {
		pm.beginTask(MessageFormat.format(DhtText.get().objectListCountingFrom
				name.abbreviate(8).name())
		try {
			final ObjectWalk walk = new ObjectWalk(reader);
			walk.setRetainBody(false);
			walk.markStart(walk.parseCommit(name));
			walk.sort(RevSort.TOPO);

			listInfo = new ObjectListInfo();
			listInfo.repository = repo;
			listInfo.startingCommit = name;

			listInfo.commits = new ObjectListInfo.Segment(OBJ_COMMIT);
			listInfo.trees = new ObjectListInfo.Segment(OBJ_TREE);
			listInfo.blobs = new ObjectListInfo.Segment(OBJ_BLOB);

			listInfo.commits.chunkStart = OBJ_COMMIT << 29;
			listInfo.trees.chunkStart = OBJ_TREE << 29;
			listInfo.blobs.chunkStart = OBJ_BLOB << 29;

			dbBuffer = db.newWriteBuffer();

			putCommits(walk
			putTreesAndBlobs(walk

			db.repository().put(repo
			dbBuffer.flush();
		} finally {
			pm.endTask();
		}
	}

	private void putCommits(final ObjectWalk walk
			throws MissingObjectException
			IOException {
		final int idsPerChunk = options.getChunkSize() / OBJECT_ID_LENGTH;
		RevObject[] commitId = new RevObject[idsPerChunk];
		int[] commitHash = new int[idsPerChunk];
		int commitCnt = 0;

		RevObject o;
		while ((o = walk.next()) != null) {
			commitId[commitCnt++] = o;
			if (commitCnt == idsPerChunk) {
				putChunk(commitId
				commitCnt = 0;
			}
			pm.update(1);
		}
		if (0 < commitCnt)
			putChunk(commitId
	}

	private void putTreesAndBlobs(final ObjectWalk walk
			throws MissingObjectException
			IOException {
		final int idsPerChunk = options.getChunkSize() / (OBJECT_ID_LENGTH + 4);
		RevObject[] treeId = new RevObject[idsPerChunk];
		RevObject[] blobId = new RevObject[idsPerChunk];
		int[] treeHash = new int[idsPerChunk];
		int[] blobHash = new int[idsPerChunk];
		int treeCnt = 0;
		int blobCnt = 0;

		RevObject o;
		while ((o = walk.nextObject()) != null) {
			switch (o.getType()) {
			case OBJ_TREE:
				treeId[treeCnt] = o;
				treeHash[treeCnt] = walk.getPathHashCode();
				if (++treeCnt == idsPerChunk) {
					putChunk(treeId
					treeCnt = 0;
				}
				pm.update(1);
				break;
			case OBJ_BLOB:
				blobId[blobCnt] = o;
				blobHash[blobCnt] = walk.getPathHashCode();
				if (++blobCnt == idsPerChunk) {
					putChunk(blobId
					blobCnt = 0;
				}
				pm.update(1);
				break;
			}
		}
		if (0 < treeCnt)
			putChunk(treeId
		if (0 < blobCnt)
			putChunk(blobId
	}

	private void putChunk(RevObject[] ids
			ObjectListInfo.Segment segment) throws DhtException {
		int cid = segment.chunkStart + segment.chunkCount;
		ObjectListChunkKey key = listInfo.getChunkKey(cid);
		ObjectListChunk list = ObjectListChunk.create(key

		segment.chunkCount++;
		segment.objectCount += cnt;

		listInfo.chunkCount++;
		listInfo.objectCount += cnt;
		listInfo.listSizeInBytes += list.getByteSize();

		db.objectList().put(list
	}
}
