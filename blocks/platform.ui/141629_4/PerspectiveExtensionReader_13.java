	    switch (relationship) {
	    	case VAL_LEFT:
		    intRelation = IPageLayout.LEFT;
		    break;
	    	case VAL_RIGHT:
		    intRelation = IPageLayout.RIGHT;
		    break;
	    	case VAL_TOP:
		    intRelation = IPageLayout.TOP;
		    break;
	    	case VAL_BOTTOM:
		    intRelation = IPageLayout.BOTTOM;
		    break;
	    	case VAL_STACK:
		    stack = true;
		    break;
	    	case VAL_FAST:
		    fast = true;
		    break;
	    	default:
		    return false;
	    }
