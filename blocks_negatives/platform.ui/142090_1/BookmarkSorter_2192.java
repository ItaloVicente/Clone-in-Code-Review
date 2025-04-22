        int column = priorities[depth];
        switch (column) {
        case DESCRIPTION: {
            String desc1 = marker1.getAttribute(IMarker.MESSAGE, "");//$NON-NLS-1$
            String desc2 = marker2.getAttribute(IMarker.MESSAGE, "");//$NON-NLS-1$
            int result = getComparator().compare(desc1, desc2);
            if (result == 0) {
