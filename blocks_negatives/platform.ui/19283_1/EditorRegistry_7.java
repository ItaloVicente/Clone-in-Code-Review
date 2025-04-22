        FileEditorMapping[] mapping = getMappingForFilename(filename);
        for (int i = 0; i < 2; i++) {
            if (mapping[i] != null) {
                String mappingKey = mappingKeyFor(mapping[i]);
                ImageDescriptor mappingImage = (ImageDescriptor) extensionImages
                        .get(key);
                if (mappingImage != null) {
