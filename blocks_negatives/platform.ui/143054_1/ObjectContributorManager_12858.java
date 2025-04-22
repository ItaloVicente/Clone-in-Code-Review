        objectLookup.put(objectClass, results);
    }

    /**
     * Get the contributions registered to this manager.
     *
     * @return an unmodifiable <code>Collection</code> containing all registered
     * contributions.  The objects in this <code>Collection</code> will be
     * <code>List</code>s containing the actual contributions.
     * @since 3.0
     */
    public Collection getContributors() {
        return Collections.unmodifiableCollection(contributors.values());
    }

    /**
     * Return the list of contributors for the supplied class.
     */
    protected List addContributorsFor(Class objectClass) {

        List classList = computeClassOrder(objectClass);
        List result = new ArrayList();
        addContributorsFor(classList, result);
        addContributorsFor(classList, result);
        return result;
    }

    /**
     * Returns true if contributors exist in the manager for
     * this object and any of it's super classes, interfaces, or
     * adapters.
     *
     * @param object the object to test
     * @return whether the object has contributors
     */
    public boolean hasContributorsFor(Object object) {

        List contributors = getContributors(object);
        return contributors.size() > 0;
    }

    /**
     * Add interface Class objects to the result list based
     * on the class hierarchy. Interfaces will be searched
     * based on their position in the result list.
     */
    private void internalComputeInterfaceOrder(Class[] interfaces, List result,
            Map seen) {
        List newInterfaces = new ArrayList(seen.size());
        for (Class currentInterface : interfaces) {
            if (seen.get(currentInterface) == null) {
                result.add(currentInterface);
                seen.put(currentInterface, currentInterface);
                newInterfaces.add(currentInterface);
            }
        }
        for (Iterator newList = newInterfaces.iterator(); newList.hasNext();) {
			internalComputeInterfaceOrder(((Class) newList.next())
                    .getInterfaces(), result, seen);
		}
    }

    /**
