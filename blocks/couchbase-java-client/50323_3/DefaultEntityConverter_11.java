        String id = source.id();
        if (id == null) {
            verifyId(entityMetadata);
            id = (String) entityMetadata.idProperty().get(document);
            if (id == null || id.isEmpty()) {
                throw new RepositoryMappingException("The @Id field cannot be null or empty.");
            }
