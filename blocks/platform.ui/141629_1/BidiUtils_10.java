			if (null != getTextDirection()) switch (getTextDirection()) {
		    	case LEFT_TO_RIGHT:
			    textDir = SWT.LEFT_TO_RIGHT;
			    break;
		    	case RIGHT_TO_LEFT:
			    textDir = SWT.RIGHT_TO_LEFT;
			    break;
		    	case AUTO:
			    textDir = SWT.LEFT_TO_RIGHT | SWT.RIGHT_TO_LEFT;
			    break;
		    	default:
			    break;
		    }
