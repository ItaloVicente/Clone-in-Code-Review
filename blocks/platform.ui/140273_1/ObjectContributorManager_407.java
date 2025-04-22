		}
		return commonClass;
	}

	private Class getCommonClass(Class class1, Class class2) {
		List list1 = computeCombinedOrder(class1);
		List list2 = computeCombinedOrder(class2);
		for (int i = 0; i < list1.size(); i++) {
			for (int j = 0; j < list2.size(); j++) {
				Class candidate1 = (Class) list1.get(i);
				Class candidate2 = (Class) list2.get(j);
				if (candidate1.equals(candidate2)) {
