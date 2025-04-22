        ColorDefinition[] children = getDescendantColors(definition);

        for (int i = 0; i < children.length; i++) {
            if (isDefault(children[i])) {
                setDescendantRegistryValues(children[i], newRGB);
                setRegistryValue(children[i], newRGB);
                colorValuesToSet.put(children[i].getId(), newRGB);
