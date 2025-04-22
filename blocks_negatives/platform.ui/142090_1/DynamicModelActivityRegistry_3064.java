                .hasNext();) {
            currentDefinition = i.next();
            if (currentDefinition.getPattern().equals(pattern)) {
                activityPatternBindingDefinitions.remove(currentDefinition);
                fireActivityRegistryChanged();
                return;
            }
        }
    }
