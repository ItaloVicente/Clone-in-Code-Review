    /**
     * Creates a new Name.
     * @param name String in the form "firstname initial lastname"
     */
    public Name(String name) {
        int index1, index2;
        index1 = name.indexOf(' ');
        if (index1 < 0)
            index1 = name.length();
        index2 = name.lastIndexOf(' ');
        if (index2 > 0)
            lastName = name.substring(index2 + 1);
        firstName = name.substring(0, index1);
        if (index1 < index2)
            initial = name.substring(index1 + 1, index2);
    }
