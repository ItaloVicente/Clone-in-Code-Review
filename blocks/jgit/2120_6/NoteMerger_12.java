
package org.eclipse.jgit.notes;

import java.io.IOException;

import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.MutableObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.jgit.merge.Merger;
import org.eclipse.jgit.merge.ThreeWayMergeStrategy;
import org.eclipse.jgit.merge.ThreeWayMerger;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;

public class NoteMapMerger {
	private static final FanoutBucket EMPTY_FANOUT = new FanoutBucket(0);

	private static final LeafBucket EMPTY_LEAF = new LeafBucket(0);

	private final Repository db;

	private final NoteMerger noteMerger;

	private final MergeStrategy nonNotesMergeStrategy;

	private final ObjectReader reader;

	private final ObjectInserter inserter;

	private final MutableObjectId objectIdPrefix;

	public NoteMapMerger(Repository db
			MergeStrategy nonNotesMergeStrategy) {
		this.db = db;
		this.reader = db.newObjectReader();
		this.inserter = db.newObjectInserter();
		this.noteMerger = noteMerger;
		this.nonNotesMergeStrategy = nonNotesMergeStrategy;
		this.objectIdPrefix = new MutableObjectId();
	}

	public NoteMapMerger(Repository db) {
		this(db
	}

	public NoteMap merge(NoteMap base
			throws IOException {
		try {
			InMemoryNoteBucket mergedBucket = merge(0
					ours.getRoot()
			inserter.flush();
			return NoteMap.newMap(mergedBucket
		} finally {
			reader.release();
			inserter.release();
		}
	}

	private InMemoryNoteBucket merge(int treeDepth
			InMemoryNoteBucket ours
			throws IOException {
		InMemoryNoteBucket result;

		if (base instanceof FanoutBucket || ours instanceof FanoutBucket
				|| theirs instanceof FanoutBucket) {
			result = mergeFanoutBucket(treeDepth
					asFanout(ours)

		} else {
			result = mergeLeafBucket(treeDepth
					(LeafBucket) ours
		}

		result.nonNotes = mergeNonNotes(nonNotes(base)
				nonNotes(theirs));
		return result;
	}

	private FanoutBucket asFanout(InMemoryNoteBucket bucket) {
		if (bucket == null)
			return EMPTY_FANOUT;
		if (bucket instanceof FanoutBucket)
			return (FanoutBucket) bucket;
		FanoutBucket fanoutBucket = ((LeafBucket) bucket).split();
		return fanoutBucket;
	}

	private static NonNoteEntry nonNotes(InMemoryNoteBucket b) {
		return b == null ? null : b.nonNotes;
	}

	private InMemoryNoteBucket mergeFanoutBucket(int treeDepth
			FanoutBucket base
			FanoutBucket ours
		FanoutBucket result = new FanoutBucket(treeDepth * 2);
		for (int i = 0; i < 256; i++) {
			NoteBucket b = base.getBucket(i);
			NoteBucket o = ours.getBucket(i);
			NoteBucket t = theirs.getBucket(i);

			if (equals(o
				addIfNotNull(result

			else if (equals(b
				addIfNotNull(result

			else if (equals(b
				addIfNotNull(result

			else {
				objectIdPrefix.setByte(treeDepth
				InMemoryNoteBucket mergedBucket = merge(treeDepth + 1
						FanoutBucket.loadIfLazy(b
						FanoutBucket.loadIfLazy(o
						FanoutBucket.loadIfLazy(t
				result.setBucket(i
			}
		}
		return result.contractIfTooSmall(objectIdPrefix
	}

	private static boolean equals(NoteBucket a
		if (a == null && b == null)
			return true;
		return a != null && b != null && a.getTreeId().equals(b.getTreeId());
	}

	private static void addIfNotNull(FanoutBucket b
		if (child == null)
			return;
		if (child instanceof InMemoryNoteBucket)
			b.setBucket(cell
		else
			b.setBucket(cell
	}

	private InMemoryNoteBucket mergeLeafBucket(int treeDepth
			LeafBucket ob
			IOException {
		bb = notNullOrEmpty(bb);
		ob = notNullOrEmpty(ob);
		tb = notNullOrEmpty(tb);

		InMemoryNoteBucket result = new LeafBucket(treeDepth * 2);
		int bi = 0
		while (bi < bb.size() || oi < ob.size() || ti < tb.size()) {
			Note b = get(bb
			Note min = min(b

			b = sameNoteOrNull(min
			o = sameNoteOrNull(min
			t = sameNoteOrNull(min

			if (sameContent(o
				result = addIfNotNull(result

			else if (sameContent(b
				result = addIfNotNull(result

			else if (sameContent(b
				result = addIfNotNull(result

			else
				result = addIfNotNull(result
						noteMerger.merge(b

			if (b != null)
				bi++;
			if (o != null)
				oi++;
			if (t != null)
				ti++;
		}
		return result;
	}

	private static LeafBucket notNullOrEmpty(LeafBucket b) {
		return b != null ? b : EMPTY_LEAF;
	}

	private static Note get(LeafBucket b
		return i < b.size() ? b.get(i) : null;
	}

	private static Note min(Note b
		Note min = b;
		if (min == null || (o != null && o.compareTo(min) < 0))
			min = o;
		if (min == null || (t != null && t.compareTo(min) < 0))
			min = t;
		return min;
	}

	private static Note sameNoteOrNull(Note min
		return sameNote(min
	}

	private static boolean sameNote(Note a
		if (a == null && b == null)
			return true;
		return a != null && b != null && AnyObjectId.equals(a
	}

	private static boolean sameContent(Note a
		if (a == null && b == null)
			return true;
		return a != null && b != null
				&& AnyObjectId.equals(a.getData()
	}

	private static InMemoryNoteBucket addIfNotNull(InMemoryNoteBucket result
			Note note) {
		if (note != null)
			return result.append(note);
		else
			return result;
	}

	private NonNoteEntry mergeNonNotes(NonNoteEntry baseList
			NonNoteEntry oursList
		if (baseList == null && oursList == null && theirsList == null)
			return null;

		ObjectId baseId = write(baseList
		ObjectId oursId = write(oursList
		ObjectId theirsId = write(theirsList
		inserter.flush();

		ObjectId resultTreeId;
		if (nonNotesMergeStrategy instanceof ThreeWayMergeStrategy) {
			ThreeWayMerger m = ((ThreeWayMergeStrategy) nonNotesMergeStrategy)
					.newMerger(db
			m.setBase(baseId);
			if (!m.merge(oursId
				throw new NotesMergeConflictException(baseList
						theirsList);

			resultTreeId = m.getResultTreeId();
		} else {
			Merger m = nonNotesMergeStrategy.newMerger(db
			if (!m.merge(new AnyObjectId[] { oursId
				throw new NotesMergeConflictException(baseList
						theirsList);
			resultTreeId = m.getResultTreeId();
		}
		AbbreviatedObjectId none = AbbreviatedObjectId.fromString("");
		return NoteParser.parse(none
	}

	private static ObjectId write(NonNoteEntry list
			throws IOException {
		LeafBucket b = new LeafBucket(0);
		b.nonNotes = list;
		return b.writeTree(ins);
	}
}
