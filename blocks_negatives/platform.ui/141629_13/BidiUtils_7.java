			if (BTD_DEFAULT.equals(handlingType)) {
				if (LEFT_TO_RIGHT.equals(getTextDirection())) {
					listener = BASE_TEXT_DIRECTION_LTR;
				} else if (RIGHT_TO_LEFT.equals(getTextDirection())) {
					listener = BASE_TEXT_DIRECTION_RTL;
				} else if (AUTO.equals(getTextDirection())) {
					listener = BASE_TEXT_DIRECTION_AUTO;
