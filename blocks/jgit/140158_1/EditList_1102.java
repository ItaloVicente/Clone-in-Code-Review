
package org.eclipse.jgit.diff;

public class Edit {
	public static enum Type {
		INSERT

		DELETE

		REPLACE

		EMPTY;
	}

	int beginA;

	int endA;

	int beginB;

	int endB;

	public Edit(int as
		this(as
	}

	public Edit(int as
		beginA = as;
		endA = ae;

		beginB = bs;
		endB = be;
	}

	public final Type getType() {
		if (beginA < endA) {
			if (beginB < endB)
				return Type.REPLACE;
				return Type.DELETE;

			if (beginB < endB)
				return Type.INSERT;
				return Type.EMPTY;
		}
	}

	public final boolean isEmpty() {
		return beginA == endA && beginB == endB;
	}

	public final int getBeginA() {
		return beginA;
	}

	public final int getEndA() {
		return endA;
	}

	public final int getBeginB() {
		return beginB;
	}

	public final int getEndB() {
		return endB;
	}

	public final int getLengthA() {
		return endA - beginA;
	}

	public final int getLengthB() {
		return endB - beginB;
	}

	public final void shift(int amount) {
		beginA += amount;
		endA += amount;
		beginB += amount;
		endB += amount;
	}

	public final Edit before(Edit cut) {
		return new Edit(beginA
	}

	public final Edit after(Edit cut) {
		return new Edit(cut.endA
	}

	public void extendA() {
		endA++;
	}

	public void extendB() {
		endB++;
	}

	public void swap() {
		final int sBegin = beginA;
		final int sEnd = endA;

		beginA = beginB;
		endA = endB;

		beginB = sBegin;
		endB = sEnd;
	}

	@Override
	public int hashCode() {
		return beginA ^ endA;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Edit) {
			final Edit e = (Edit) o;
			return this.beginA == e.beginA && this.endA == e.endA
					&& this.beginB == e.beginB && this.endB == e.endB;
		}
		return false;
	}

	@SuppressWarnings("nls")
	@Override
	public String toString() {
		final Type t = getType();
		return t + "(" + beginA + "-" + endA + "
	}
}
