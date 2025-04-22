package org.eclipse.jgit.blame;

class MockDifference implements IDifference {

	final int startA;

	final int endA;

	final int startB;

	final int endB;

	public MockDifference(int startA
		this.startA = startA;
		this.endA = endA;
		this.startB = startB;
		this.endB = endB;
	}

	public int getStartA() {
		return startA;
	}

	public int getStartB() {
		return startB;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + endA;
		result = prime * result + endB;
		result = prime * result + startA;
		result = prime * result + startB;
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
		MockDifference other = (MockDifference) obj;
		if (endA != other.endA)
			return false;
		if (endB != other.endB)
			return false;
		if (startA != other.startA)
			return false;
		if (startB != other.startB)
			return false;
		return true;
	}

	public int getLengthA() {
		return endA < 0 ? 0 : endA - startA + 1;
	}

	public int getLengthB() {
		return endB < 0 ? 0 : endB - startB + 1;
	}

	MockDifference inverted() {
		return new MockDifference(getStartB()
				getStartA()
	}

}
