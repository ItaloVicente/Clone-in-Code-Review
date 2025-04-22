		if (scaleFactor != 1f) {
			float graceHeight = 3 / scaleFactor;
			messageRect.x = Math.round(messageRect.x * scaleFactor);
			messageRect.y = Math.round(messageRect.y * scaleFactor - graceHeight);
			messageRect.width = Math.round(messageRect.width * scaleFactor);
			messageRect.height = Math.round(messageRect.height * scaleFactor + graceHeight);
		}
