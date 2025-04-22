			if (o1.length() == 0)
				return -1;
			if (o2.length() == 0)
				return 1;

			String[] o1Parts = BETWEEN_PARTS.split(o1);
			String[] o2Parts = BETWEEN_PARTS.split(o2);

			for (int i = 0; i < o1Parts.length; i++) {
				if (i >= o2Parts.length)
					return 1;

				String o1Part = o1Parts[i];
				String o2Part = o2Parts[i];

				int result;

				if (Character.isDigit(o1Part.charAt(0)) && Character.isDigit(o2Part.charAt(0))) {
					o1Part = LEADING_ZEROS.matcher(o1Part).replaceFirst(""); //$NON-NLS-1$
					o2Part = LEADING_ZEROS.matcher(o2Part).replaceFirst(""); //$NON-NLS-1$
					result = o1Part.length() - o2Part.length();
					if (result == 0)
						result = o1Part.compareTo(o2Part);
				} else {
					result = o1Part.compareTo(o2Part);
				}

				if (result != 0)
					return result;
