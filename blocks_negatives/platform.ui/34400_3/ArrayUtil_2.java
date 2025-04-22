    /**
     * Returns whether an array contains a given object.
     *
     * @param array the input array
     * @param element the test object
     * @return <code>true</code> if the array contains the object,
     * 		<code>false</code> otherwise.
     */
    public static boolean contains(Object[] array, Object element) {
        if (array == null || element == null)
            return false;
        else {
            for (int i = 0; i < array.length; i++)
                if (array[i] == element)
                    return true;
            return false;
        }
    }
