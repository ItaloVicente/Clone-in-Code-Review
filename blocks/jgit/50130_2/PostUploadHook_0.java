
package org.eclipse.jgit.storage.pack;

import static org.eclipse.jgit.lib.Constants.OBJ_BLOB;
import static org.eclipse.jgit.lib.Constants.OBJ_COMMIT;
import static org.eclipse.jgit.lib.Constants.OBJ_TAG;
import static org.eclipse.jgit.lib.Constants.OBJ_TREE;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.pack.CachedPack;
import org.eclipse.jgit.lib.ObjectId;

public class PackStatistics {
	public static class ObjectType {
		public static class Accumulator {
			public long cntObjects;

			public long cntDeltas;

			public long reusedObjects;

			public long reusedDeltas;

			public long bytes;

			public long deltaBytes;
		}

		private ObjectType.Accumulator objectType;

		public ObjectType(ObjectType.Accumulator accumulator) {
			objectType = accumulator;
		}

		public long getObjects() {
			return objectType.cntObjects;
		}

		public long getDeltas() {
			return objectType.cntDeltas;
		}

		public long getReusedObjects() {
			return objectType.reusedObjects;
		}

		public long getReusedDeltas() {
			return objectType.reusedDeltas;
		}

		public long getBytes() {
			return objectType.bytes;
		}

		public long getDeltaBytes() {
			return objectType.deltaBytes;
		}
	}

	public static class Accumulator {
		public Set<ObjectId> interestingObjects;

		public Set<ObjectId> uninterestingObjects;

		public List<CachedPack> reusedPacks;

		public int depth;

		public int deltaSearchNonEdgeObjects;

		public int deltasFound;

		public long totalObjects;

		public long bitmapIndexMisses;

		public long totalDeltas;

		public long reusedObjects;

		public long reusedDeltas;

		public long totalBytes;

		public long thinPackBytes;

		public long timeCounting;

		public long timeSearchingForReuse;

		public long timeSearchingForSizes;

		public long timeCompressing;

		public long timeWriting;

		public ObjectType.Accumulator[] objectTypes;

		{
			objectTypes = new ObjectType.Accumulator[5];
			objectTypes[OBJ_COMMIT] = new ObjectType.Accumulator();
			objectTypes[OBJ_TREE] = new ObjectType.Accumulator();
			objectTypes[OBJ_BLOB] = new ObjectType.Accumulator();
			objectTypes[OBJ_TAG] = new ObjectType.Accumulator();
		}
	}

	private Accumulator statistics;

	public PackStatistics(Accumulator accumulator) {
		statistics = accumulator;
	}

	public Set<ObjectId> getInterestingObjects() {
		return statistics.interestingObjects;
	}

	public Set<ObjectId> getUninterestingObjects() {
		return statistics.uninterestingObjects;
	}

	public List<CachedPack> getReusedPacks() {
		return statistics.reusedPacks;
	}

	public int getDeltaSearchNonEdgeObjects() {
		return statistics.deltaSearchNonEdgeObjects;
	}

	public int getDeltasFound() {
		return statistics.deltasFound;
	}

	public long getTotalObjects() {
		return statistics.totalObjects;
	}

	public long getBitmapIndexMisses() {
		return statistics.bitmapIndexMisses;
	}

	public long getTotalDeltas() {
		return statistics.totalDeltas;
	}

	public long getReusedObjects() {
		return statistics.reusedObjects;
	}

	public long getReusedDeltas() {
		return statistics.reusedDeltas;
	}

	public long getTotalBytes() {
		return statistics.totalBytes;
	}

	public long getThinPackBytes() {
		return statistics.thinPackBytes;
	}

	public ObjectType byObjectType(int typeCode) {
		return new ObjectType(statistics.objectTypes[typeCode]);
	}

	public boolean isShallow() {
		return statistics.depth > 0;
	}

	public int getDepth() {
		return statistics.depth;
	}

	public long getTimeCounting() {
		return statistics.timeCounting;
	}

	public long getTimeSearchingForReuse() {
		return statistics.timeSearchingForReuse;
	}

	public long getTimeSearchingForSizes() {
		return statistics.timeSearchingForSizes;
	}

	public long getTimeCompressing() {
		return statistics.timeCompressing;
	}

	public long getTimeWriting() {
		return statistics.timeWriting;
	}

	public long getTimeTotal() {
		return statistics.timeCounting + statistics.timeSearchingForReuse
				+ statistics.timeSearchingForSizes + statistics.timeCompressing
				+ statistics.timeWriting;
	}

	public double getTransferRate() {
		return getTotalBytes() / (getTimeWriting() / 1000.0);
	}

	public String getMessage() {
		return MessageFormat.format(JGitText.get().packWriterStatistics
				Long.valueOf(statistics.totalObjects)
				Long.valueOf(statistics.totalDeltas)
				Long.valueOf(statistics.reusedObjects)
				Long.valueOf(statistics.reusedDeltas));
	}

	public Map<Integer
		HashMap<Integer
		map.put(Integer.valueOf(OBJ_BLOB)
				statistics.objectTypes[OBJ_BLOB]));
		map.put(Integer.valueOf(OBJ_COMMIT)
				statistics.objectTypes[OBJ_COMMIT]));
		map.put(Integer.valueOf(OBJ_TAG)
				statistics.objectTypes[OBJ_TAG]));
		map.put(Integer.valueOf(OBJ_TREE)
				statistics.objectTypes[OBJ_TREE]));
		return map;
	}
}
