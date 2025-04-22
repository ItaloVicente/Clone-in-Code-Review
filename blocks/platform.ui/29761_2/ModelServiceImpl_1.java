		Collection<FLAGS> flags = new HashSet<FLAGS>();

		if ((searchFlags & IN_ANY_PERSPECTIVE) != 0) {
			flags.add(FLAGS.IN_ANY_PERSPECTIVE);
		}
		if ((searchFlags & IN_ACTIVE_PERSPECTIVE) != 0) {
			flags.add(FLAGS.IN_ACTIVE_PERSPECTIVE);
		}
		if (searchFlags == ANYWHERE) {
			flags.add(FLAGS.ANYWHERE);
		}

		if ((searchFlags & IN_MAIN_MENU) != 0) {
			flags.add(FLAGS.IN_MAIN_MENU);
		}
		if ((searchFlags & IN_PART) != 0) {
			flags.add(FLAGS.IN_PART);
		}

		for (FLAGS flag : flags) {
			findElementsRecursive((EObject) searchRoot, clazz, matcher, elements, flag);
		}

