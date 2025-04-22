
			if (latestFilter != null) {
				String pattern = latestFilter.getPattern();
				int patternDot = pattern.lastIndexOf('.');
				String patternNoExtension = patternDot == -1 ? pattern : pattern.substring(0, patternDot);
				boolean m1 = patternNoExtension.equals(n1);
				boolean m2 = patternNoExtension.equals(n2);
				if (m1 && m2)
					return 0;
				if (m1)
					return -1;
				if (m2)
					return 1;
			}

