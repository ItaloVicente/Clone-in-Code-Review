                if (!result.contains(adapter)) {
                    result.add(adapter);
                }
            }
        }
        return new ArrayList(result);
    }

    /**
     * Returns the common denominator resource class for the given
     * collection of objects.
     * Do not return a resource class if the objects are resources
     * themselves so as to prevent double registration of actions.
     */
    private Class getCommonResourceClass(List objects) {
        if (objects == null || objects.isEmpty()) {
            return null;
        }
        Class resourceClass = LegacyResourceSupport.getResourceClass();
        if (resourceClass == null) {
            return null;
        }

        List testList = new ArrayList(objects.size());

        for (int i = 0; i < objects.size(); i++) {
            Object object = objects.get(i);

            if (object instanceof IAdaptable) {
                if (resourceClass.isInstance(object)) {
                    continue;
                }

                Object resource = LegacyResourceSupport
                        .getAdaptedContributorResource(object);

                if (resource == null) {
                    return null;
                }
                testList.add(resource);
            } else {
                return null;
            }
        }

        return getCommonClass(testList);
    }

    /**
     * Return the ResourceMapping class if the elements all adapt to it.
     */
    private Class getResourceMappingClass(List objects) {
        if (objects == null || objects.isEmpty()) {
            return null;
        }
        Class resourceMappingClass = LegacyResourceSupport
                .getResourceMappingClass();
        if (resourceMappingClass == null) {
            return null;
        }

        for (int i = 0; i < objects.size(); i++) {
            Object object = objects.get(i);

            if (object instanceof IAdaptable) {
                if (resourceMappingClass.isInstance(object)) {
                    continue;
                }

                Object resourceMapping = LegacyResourceSupport
                        .getAdaptedContributorResourceMapping(object);

                if (resourceMapping == null) {
                    return null;
                }
            } else {
                return null;
            }
        }
        return resourceMappingClass;
    }

    /**
     * Returns the common denominator class for the given
     * collection of objects.
     */
    private Class getCommonClass(List objects) {
        if (objects == null || objects.isEmpty()) {
