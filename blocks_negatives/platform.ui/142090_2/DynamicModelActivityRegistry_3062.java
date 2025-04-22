            currentCategory = i.next();
            if (currentCategory.getId().equals(categoryId)) {
                categoryDefinitions.remove(currentCategory);
                categoryDefinitions.add(new CategoryDefinition(categoryId,
                        categoryName, currentCategory.getSourceId(),
                        currentCategory.getDescription()));
                fireActivityRegistryChanged();
                return;
            }
        }
    }
