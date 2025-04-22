			String message = commitMessageComponent.getStatus().getMessage();
			boolean needsRedraw = false;
			if (message != null) {
				warningLabel.showMessage(message);
				needsRedraw = true;
			} else {
				needsRedraw = warningLabel.getVisible();
				warningLabel.hideMessage();
			}
			if (needsRedraw)
				commitMessageSection.redraw();
