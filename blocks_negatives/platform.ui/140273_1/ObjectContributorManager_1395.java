        if (allSameClass(objects)) {

        	Class clazz = objects.get(0).getClass();
        	commonAdapters.addAll(Arrays.asList(Platform.getAdapterManager().computeAdapterTypes(clazz)));
        	List result = new ArrayList(1);
        	result.add(clazz);
        	return result;
        }

        List classes = computeClassOrder(objects.get(0).getClass());
        List adapters = computeAdapterOrder(classes);
        List interfaces = computeInterfaceOrder(classes);

        List lastCommonTypes = new ArrayList();

        boolean classesEmpty = classes.isEmpty();
        boolean interfacesEmpty = interfaces.isEmpty();

        for (int i = 1; i < objects.size(); i++) {
            List otherClasses = computeClassOrder(objects.get(i).getClass());
            if (!classesEmpty) {
                classesEmpty = extractCommonClasses(classes, otherClasses);
            }

            List otherInterfaces = computeInterfaceOrder(otherClasses);
            if (!interfacesEmpty) {
                interfacesEmpty = extractCommonClasses(interfaces,
                        otherInterfaces);
            }

            List classesAndInterfaces = new ArrayList(otherClasses);
            if (otherInterfaces != null) {
