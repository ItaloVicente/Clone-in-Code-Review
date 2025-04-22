	private int compareVersion(String version, int... numbers) {
		try (Scanner scanner = new Scanner(version)) {
			scanner.useDelimiter("\\."); //$NON-NLS-1$

			for (int number : numbers) {
				if (!scanner.hasNextInt())
					return -1;

				int result = Integer.compare(scanner.nextInt(), number);
				if (result != 0)
					return result;
			}

			while (scanner.hasNextInt())
				if (scanner.nextInt() > 0)
					return 1;
		}

		return 0;
	}

