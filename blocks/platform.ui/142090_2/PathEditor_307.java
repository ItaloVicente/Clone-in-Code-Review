		StringTokenizer st = new StringTokenizer(stringList, File.pathSeparator
				+ "\n\r");//$NON-NLS-1$
		ArrayList<Object> v = new ArrayList<>();
		while (st.hasMoreElements()) {
			v.add(st.nextElement());
		}
		return v.toArray(new String[v.size()]);
	}
