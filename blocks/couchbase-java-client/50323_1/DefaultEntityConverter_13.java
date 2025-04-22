            if (source.content() != null) {
                for (PropertyMetadata propertyMetadata : entityMetadata.properties()) {
                    String fieldName = propertyMetadata.name();
                    if (source.content().containsKey(fieldName)) {
                        propertyMetadata.set(source.content().get(fieldName), instance);
                    }
