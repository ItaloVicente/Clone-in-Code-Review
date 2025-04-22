        int nSize = fonts.size();
        FontDefinition[] retArray = new FontDefinition[nSize];
        fonts.toArray(retArray);
        Arrays.sort(retArray, ID_COMPARATOR);
        return retArray;
    }

    @Override
