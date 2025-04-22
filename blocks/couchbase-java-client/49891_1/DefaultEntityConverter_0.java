

    private static void verifyId(final EntityMetadata entityMetadata) {
        if (!entityMetadata.hasIdProperty()) {
            throw new RepositoryMappingException("No field annotated with @Id present.");
        }

        if (entityMetadata.idProperty().type() != String.class) {
            throw new RepositoryMappingException("The @Id Field needs to be of type String.");
        }
    }
