		Arrays.sort(array, (a, b) -> {
			String id = "org.eclipse.ui.navigator.resources."; //$NON-NLS-1$
			if (a.getClass().getName().startsWith(id))
				return -1;
			if (b.getClass().getName().startsWith(id))
				return 1;
			return a.getClass().getName().compareTo(b.getClass().getName());
