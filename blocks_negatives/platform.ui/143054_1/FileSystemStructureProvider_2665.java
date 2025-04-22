        File folder = (File) element;
        String[] children = folder.list();
        int childrenLength = children == null ? 0 : children.length;
        List result = new ArrayList(childrenLength);

        for (int i = 0; i < childrenLength; i++) {
        	File file = new File(folder, children[i]);
        	if(isRecursiveLink(file))
        		continue;
        	result.add(file);
