		objectLookup.put(objectClass, results);
	}

	public Collection getContributors() {
		return Collections.unmodifiableCollection(contributors.values());
	}

	protected List addContributorsFor(Class objectClass) {

		List classList = computeClassOrder(objectClass);
		List result = new ArrayList();
		addContributorsFor(classList, result);
		classList = computeInterfaceOrder(classList); // interfaces
		addContributorsFor(classList, result);
		return result;
	}

	public boolean hasContributorsFor(Object object) {

		List contributors = getContributors(object);
		return contributors.size() > 0;
	}

	private void internalComputeInterfaceOrder(Class[] interfaces, List result, Map seen) {
		List newInterfaces = new ArrayList(seen.size());
		for (Class currentInterface : interfaces) {
			if (seen.get(currentInterface) == null) {
				result.add(currentInterface);
				seen.put(currentInterface, currentInterface);
				newInterfaces.add(currentInterface);
			}
		}
		for (Iterator newList = newInterfaces.iterator(); newList.hasNext();) {
			internalComputeInterfaceOrder(((Class) newList.next()).getInterfaces(), result, seen);
		}
	}

