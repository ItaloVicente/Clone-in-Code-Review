        if (list != null) {
            list.removeAll();
            String s = getPreferenceStore().getDefaultString(
                    getPreferenceName());
            String[] array = parseString(s);
            for (String element : array) {
                list.add(element);
            }
        }
    }

    @Override
