    /**
     * Returns whether two arrays are equal.  They must
     * have the same size and the same contents.
     *
     * @param one the first array
     * @param two the second array
     * @return <code>true</code> if the array are equal,
     * 		<code>false</code> otherwise.
     */
    public static boolean equals(Object[] one, Object[] two) {
        if (one.length != two.length)
            return false;
        else {
            for (int i = 0; i < one.length; i++)
                if (one[i] != two[i])
                    return false;
            return true;
        }
    }
