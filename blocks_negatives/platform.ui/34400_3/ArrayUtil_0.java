    /**
     * Returns a random object chosen from an array.
     *
     * @param array the input array
     * @return a random object in the array
     */
    public static Object pickRandom(Object[] array) {
        int num = randomBox.nextInt(array.length);
        return array[num];
    }
