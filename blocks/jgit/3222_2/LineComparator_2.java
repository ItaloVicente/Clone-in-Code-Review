package org.eclipse.jgit.lines;

public class Line {

	private String content;

	private int start;

	private int end;

	private int number;

	private int[] numbers;

	public Line() {
		this.numbers = new int[0];
	}

	public Line(int start
		this(start
	}

	public Line(int start
		this();
		this.start = start;
		this.end = end;
		this.content = content;
	}

	public int getNumber() {
		return this.number;
	}

	public int getNumber(int revision) {
		if (revision < this.start || revision > this.end)
			return -1;
		revision -= this.start;
		return numbers[revision];
	}

	public boolean overlaps(Line line) {
		if (start > line.start)
			return start <= line.end;
		else if (start < line.start)
			return end >= line.start;
		else
			return true;
	}

	public Line setNumber(int number) {
		this.number = number;
		int[] newNumbers = new int[numbers.length + 1];
		System.arraycopy(numbers
		newNumbers[newNumbers.length - 1] = number;
		numbers = newNumbers;
		return this;
	}

	public Line setContent(String content) {
		this.content = content;
		return this;
	}

	public String getContent() {
		return this.content;
	}

	public Line setEnd(int end) {
		this.end = end;
		return this;
	}

	public int getEnd() {
		return this.end;
	}

	public Line setStart(int start) {
		this.start = start;
		return this;
	}

	public int getStart() {
		return this.start;
	}

	public int getAge() {
		return (this.end - this.start) + 1;
	}

	public String toString() {
		return this.start + "-" + this.end + ": " + this.content;
	}

	public int hashCode() {
		return toString().hashCode();
	}

	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		else if (obj instanceof Line) {
			Line other = (Line) obj;
			return this.start == other.start && this.end == other.end
					&& this.number == other.number;
		}
		return false;
	}
}
