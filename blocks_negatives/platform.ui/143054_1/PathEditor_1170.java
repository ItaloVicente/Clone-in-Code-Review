        StringTokenizer st = new StringTokenizer(stringList, File.pathSeparator
        ArrayList<Object> v = new ArrayList<>();
        while (st.hasMoreElements()) {
            v.add(st.nextElement());
        }
        return v.toArray(new String[v.size()]);
    }
