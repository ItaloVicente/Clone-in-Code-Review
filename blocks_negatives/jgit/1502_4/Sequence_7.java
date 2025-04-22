	public int size();

	/**
	 * Determine if the i-th member is equal to the j-th member.
	 * <p>
	 * Implementations must ensure <code>equals(thisIdx,other,otherIdx)</code>
	 * returns the same as <code>other.equals(otherIdx,this,thisIdx)</code>.
	 *
	 * @param thisIdx
	 *            index within <code>this</code> sequence; must be in the range
	 *            <code>[ 0, this.size() )</code>.
	 * @param other
	 *            another sequence; must be the same implementation class, that
	 *            is <code>this.getClass() == other.getClass()</code>.
	 * @param otherIdx
	 *            index within <code>other</code> sequence; must be in the range
	 *            <code>[ 0, other.size() )</code>.
	 * @return true if the elements are equal; false if they are not equal.
	 */
	public boolean equals(int thisIdx, Sequence other, int otherIdx);
