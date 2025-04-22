            if (entityMetadata.hasIdProperty()) {
                entityMetadata.idProperty().set(source.id(), instance);
                if (entityMetadata.hasCasProperty() && source.cas() != 0) {
                    entityMetadata.casProperty().set(source.cas(), instance);
                }
            }

            return EntityDocument.create(source.id(), source.expiry(), instance, source.cas());
