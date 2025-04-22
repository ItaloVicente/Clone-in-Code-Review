	/**
	 * Checks to see if a flag is contained in a flag field. The flag field must be converted to
	 * an integer before calling this function
	 *
	 * @param f The integer value of the flag field in a tap packet
	 *
	 * @return Returns true if the flag is contained in the flag field
	 */
	boolean hasFlag(int f) {
		if ((f & (int) flag) > 0) {
			return true;
		}
		return false;
	}
