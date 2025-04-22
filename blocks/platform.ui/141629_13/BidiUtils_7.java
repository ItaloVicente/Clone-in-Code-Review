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
						listener = BASE_TEXT_DIRECTION_LTR;
						break;
					case RIGHT_TO_LEFT:
						listener = BASE_TEXT_DIRECTION_RTL;
						break;
					case AUTO:
						listener = BASE_TEXT_DIRECTION_AUTO;
						break;
					default:
						break;
					}
