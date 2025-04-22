package org.eclipse.jgit.blame;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CommonChunk {
	final int astart;

	final int bstart;

	final int length;

	public CommonChunk(int astart
		super();
		this.astart = astart;
		this.bstart = bstart;
		this.length = length;
	}

	@Override
	public String toString() {
		return String.format("Common <%d:%d  %d>"
				Integer.valueOf(bstart)
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + astart;
		result = prime * result + bstart;
		result = prime * result + length;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommonChunk other = (CommonChunk) obj;
		if (astart != other.astart)
			return false;
		if (bstart != other.bstart)
			return false;
		if (length != other.length)
			return false;
		return true;
	}

	public int getAstart() {
		return astart;
	}

	public int getBstart() {
		return bstart;
	}

	public int getLength() {
		return length;
	}

	public static List<CommonChunk> computeCommonChunks(
			List<? extends IDifference> differences
		List<CommonChunk> result = new LinkedList<CommonChunk>();
		if (differences.isEmpty()) {
			result.add(new CommonChunk(0
			return result;
		}
		IDifference firstDifference = differences.get(0);
		int commonLengthA = firstDifference.getStartA();
		int commonLengthB = firstDifference.getStartB();
		if (commonLengthA != commonLengthB) {
			throw new RuntimeException("lengths not equal: " + commonLengthA
					+ "!=" + commonLengthB);
		}
		int commonPrefixLength = commonLengthA;
		if (commonPrefixLength > 0) {
			result.add(new CommonChunk(0
		}
		Iterator<? extends IDifference> it = differences.iterator();
		IDifference previousDifference = it.next();

		for (; it.hasNext();) {
			IDifference nextDifference = it.next();
			int firstCommonLineA = previousDifference.getStartA()
					+ previousDifference.getLengthA();
			int firstCommonLineB = previousDifference.getStartB()
					+ previousDifference.getLengthB();
			commonLengthA = nextDifference.getStartA() - firstCommonLineA;
			commonLengthB = nextDifference.getStartB() - firstCommonLineB;
			if (commonLengthA != commonLengthB) {
				throw new RuntimeException("lengths not equal: "
						+ commonLengthA + "!=" + commonLengthB);
			}
			result.add(new CommonChunk(firstCommonLineA
					commonLengthA));
			previousDifference = nextDifference;
		}

		IDifference lastDifference = differences.get(differences.size() - 1);
		int firstCommonLineA = lastDifference.getStartA()
				+ lastDifference.getLengthA();
		int firstCommonLineB = lastDifference.getStartB()
				+ lastDifference.getLengthB();
		commonLengthA = lengthA - firstCommonLineA;
		commonLengthB = lengthB - firstCommonLineB;
		if (commonLengthA != commonLengthB) {
			throw new RuntimeException("lengths not equal: " + commonLengthA
					+ "!=" + commonLengthB);
		}
		int commonSuffixLength = commonLengthA;

		if (commonSuffixLength > 0) {
			result.add(new CommonChunk(firstCommonLineA
					commonSuffixLength));
		}

		return result;
	}


}
