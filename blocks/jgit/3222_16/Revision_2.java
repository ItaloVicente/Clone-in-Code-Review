package org.eclipse.jgit.blame;

import java.util.Arrays;

import org.eclipse.jgit.revwalk.RevCommit;

public class Line {

	private RevCommit startCommit;

	private int[] numbers = new int[0];

	public Line(RevCommit start) {
		startCommit = start;
	}

	public int getNumber() {
		return getNumber(numbers.length - 1);
	}

	public int getNumber(int index) {
		return numbers[index];
	}

	Line setNumber(int number) {
		int[] newNumbers = new int[numbers.length + 1];
		newNumbers[0] = number;
		System.arraycopy(numbers
		numbers = newNumbers;
		return this;
	}

	Line setStart(RevCommit start) {
		startCommit = start;
		return this;
	}

	public RevCommit getStart() {
		return startCommit;
	}

	public int getAge() {
		return numbers.length;
	}

	public String toString() {
		return "Added in: " + startCommit.abbreviate(7) + "
				+ numbers.length;
	}

	public int hashCode() {
		return (((startCommit.hashCode() * 31) + numbers.length) * 31)
				+ getNumber();
	}

	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		else if (obj instanceof Line) {
			Line other = (Line) obj;
			return startCommit.equals(other.startCommit)
					&& Arrays.equals(numbers
		}
		return false;
	}
}
