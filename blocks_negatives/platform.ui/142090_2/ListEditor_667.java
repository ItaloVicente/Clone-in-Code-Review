        if (list != null) {
            String s = getPreferenceStore().getString(getPreferenceName());
            String[] array = parseString(s);
            for (String element : array) {
                list.add(element);
            }
        }
    }

    @Override
