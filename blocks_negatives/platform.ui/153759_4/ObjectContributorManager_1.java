	private Class getCommonClass(Class class1, Class class2) {
		List list1 = computeCombinedOrder(class1);
		List list2 = computeCombinedOrder(class2);
		for (Object element1 : list1) {
			for (Object element2 : list2) {
				Class candidate1 = (Class) element1;
				Class candidate2 = (Class) element2;
