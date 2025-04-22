		where = setRelToInfo(info);

		showFeedback();
	}

	private int setRelToInfo(DnDInfo info) {
		if (relToElement instanceof MPerspective || relToElement instanceof MArea) {
			return where;
		} else if (relToElement instanceof MPartStack) {
			ratio = 0.5f;
			Point p = info.cursorPos;
			int dTop = p.y - trackRect.y;
			int dBottom = (trackRect.y + trackRect.height) - p.y;
			int dLeft = p.x - trackRect.x;
			int dRight = (trackRect.x + trackRect.width) - p.x;

			if (trackRect.width >= trackRect.height) {
				int thirdOfWidth = trackRect.width / 3;
				if (dLeft < thirdOfWidth)
					return EModelService.LEFT_OF;
				else if (dRight < thirdOfWidth)
					return EModelService.RIGHT_OF;
				else {
					return dTop < dBottom ? EModelService.ABOVE : EModelService.BELOW;
				}
			}

			int thirdOfHeight = trackRect.height / 3;
			if (dTop < thirdOfHeight)
				return EModelService.ABOVE;
			else if (dBottom < thirdOfHeight)
				return EModelService.BELOW;
			else if (relToElement != null) {
				return dLeft < dRight ? EModelService.LEFT_OF : EModelService.RIGHT_OF;
			}
		}

		return -1;
