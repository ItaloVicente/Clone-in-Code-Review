package org.eclipse.jgit.blame;

public class Line {

	private int startRevision;

	private int endRevision;

	private int[] numbers = new int[0];

	public Line(int start) {
		this(start
	}

	public Line(int start
		this.startRevision = start;
		this.endRevision = end;
	}

	public int getNumber() {
		return getNumber(getEnd());
	}

	public int getNumber(int revision) {
		if (revision < this.startRevision || revision > this.endRevision)
			return -1;
		revision -= this.startRevision;
		return numbers[revision];
	}

	public boolean overlaps(Line line) {
		if (startRevision > line.startRevision)
			return startRevision <= line.endRevision;
		else if (startRevision < line.startRevision)
			return endRevision >= line.startRevision;
		else
			return true;
	}

	public Line setNumber(int number) {
		int[] newNumbers = new int[numbers.length + 1];
		System.arraycopy(numbers
		newNumbers[newNumbers.length - 1] = number;
		numbers = newNumbers;
		return this;
	}

	public Line setEnd(int end) {
		this.endRevision = end;
		return this;
	}

	public int getEnd() {
		return this.endRevision;
	}

	public Line setStart(int start) {
		this.startRevision = start;
		return this;
	}

	public int getStart() {
		return this.startRevision;
	}

	public int getAge() {
		return (this.endRevision - this.startRevision) + 1;
	}

	@Override
	public String toString() {
		return this.startRevision + "-" + this.endRevision + ": " + getNumber();
	}

	@Override
	public int hashCode() {
		return (((this.startRevision * 31) + this.endRevision) * 31)
				+ getNumber();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		else if (obj instanceof Line) {
			Line other = (Line) obj;
			return this.startRevision == other.startRevision
					&& this.endRevision == other.endRevision
					&& getNumber() == other.getNumber();
		}
		return false;
	}
}
