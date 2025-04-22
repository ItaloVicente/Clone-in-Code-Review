			} else if (VISUAL_LEFT_TO_RIGHT.equals(handlingType)) {
				listener = VISUAL_TEXT_DIRECTION_LTR;
			} else if (VISUAL_RIGHT_TO_LEFT.equals(handlingType)) {
				listener = VISUAL_TEXT_DIRECTION_RTL;
			} else {
				Object handler = structuredTextSegmentListeners.get(handlingType);
				if (handler != null) {
					listener = (SegmentListener) handler;
				} else {
					listener = new StructuredTextSegmentListener(handlingType);
					structuredTextSegmentListeners.put(handlingType, listener);
				}
			}
