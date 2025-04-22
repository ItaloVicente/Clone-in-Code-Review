        for (int i = 0; i < types.length; ++i) {
            MarkerType type = types[i];
            String[] supers = type.getSupertypeIds();
            for (int j = 0; j < supers.length; ++j) {
                if (supers[j].equals(id)) {
                    result.add(type);
