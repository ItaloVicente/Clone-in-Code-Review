    /**
     * Returns whether an array is not null and
     * each object in the array is not null.
     *
     * @param array the input array
     * @return <code>true or false</code>
     */
    public static boolean checkNotNull(Object[] array) {
        if (array == null)
            return false;
        else {
            for (int i = 0; i < array.length; i++)
                if (array[i] == null)
                    return false;
            return true;
        }
    }
