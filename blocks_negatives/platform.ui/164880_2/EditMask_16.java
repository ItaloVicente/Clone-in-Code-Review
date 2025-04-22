			if (!updating) {
				replacedSelectedText = false;
				if (selectionRange.y - selectionRange.x > 0 && e.text.length() > 0) {
					replacedSelectedText = true;
				}
				SWTUtil.greedyExec(Display.getCurrent(), updateTextField);
