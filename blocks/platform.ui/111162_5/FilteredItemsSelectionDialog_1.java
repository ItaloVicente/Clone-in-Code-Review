			if (filter != null) {
				String filterPattern = filter.getPattern();
				boolean m1 = filterPattern.equals(getElementName(o1));
				boolean m2 = filterPattern.equals(getElementName(o2));
				if (m1 && m2)
					return 0;
				if (m1 && !m2)
					return -1;
				if (m2 && !m1)
					return 1;
			}

