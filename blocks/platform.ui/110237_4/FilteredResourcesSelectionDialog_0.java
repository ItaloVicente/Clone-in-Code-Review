			if (latestFilter != null) {
				String filterPattern = latestFilter.getPattern();
				boolean m1 = filterPattern.equals(s1);
				boolean m2 = filterPattern.equals(s2);
				if (m1 && m2)
					return 0;
				if (m1)
					return -1;
				if (m2)
					return 1;
			}
