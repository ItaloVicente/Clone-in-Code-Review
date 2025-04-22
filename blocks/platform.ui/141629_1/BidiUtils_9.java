			    }
			    break;
		    	case VISUAL_LEFT_TO_RIGHT:
			    listener = VISUAL_TEXT_DIRECTION_LTR;
			    break;
		    	case VISUAL_RIGHT_TO_LEFT:
			    listener = VISUAL_TEXT_DIRECTION_RTL;
			    break;
		    	default:
			    Object handler = structuredTextSegmentListeners.get(handlingType);
			    if (handler != null) {
				listener = (SegmentListener) handler;
			    } else {
				listener = new StructuredTextSegmentListener(handlingType);
				structuredTextSegmentListeners.put(handlingType, listener);
			    }
			    break;
		    }
