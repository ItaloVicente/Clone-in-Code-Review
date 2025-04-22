		    int offset = -1;
		    try {
		        offset = text2.getOffsetAtLocation(new Point(e.x, e.y));
		    } catch (IllegalArgumentException ex) {
		    }
		    if (offset == -1) {
