			if (null == handlingType) {
			    Object handler = structuredTextSegmentListeners.get(handlingType);
			    if (handler != null) {
				listener = (SegmentListener) handler;
			    } else {
				listener = new StructuredTextSegmentListener(handlingType);
				structuredTextSegmentListeners.put(handlingType, listener);
			    }
			} else switch (handlingType) {
		    	case BTD_DEFAULT:
			    if (null != getTextDirection()) {
				switch (getTextDirection()) {
				    case LEFT_TO_RIGHT:
