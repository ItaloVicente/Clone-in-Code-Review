				if (null == handlingType) {
				    color = SWT.COLOR_CYAN;
				} else switch (handlingType) {
			    	case LEFT_TO_RIGHT:
				    color = SWT.COLOR_RED;
				    break;
			    	case RIGHT_TO_LEFT:
				    color = SWT.COLOR_GREEN;
				    break;
			    	case BTD_DEFAULT:
				    color = SWT.COLOR_YELLOW;
				    break;
			    	case AUTO:
				    color = SWT.COLOR_MAGENTA;
				    break;
			    	default:
				    color = SWT.COLOR_CYAN;
				    break;
			    }
