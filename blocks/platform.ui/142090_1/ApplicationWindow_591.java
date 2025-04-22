			Point result = new Point(0, 0);
			Control[] ws = composite.getChildren();
			for (int i = 0; i < ws.length; i++) {
				Control w = ws[i];

				boolean hide = false;
				if (getToolBarControl() == w) {
					if (!toolBarChildrenExist()) {
						hide = true;
						result.y += BAR_SIZE; // REVISIT
					}
				} else if (getCoolBarControl() == w) {
					if (!coolBarChildrenExist()) {
						hide = true;
						result.y += BAR_SIZE;
					}
				} else if (statusLineManager != null
						&& statusLineManager.getControl() == w) {
				} else if (i > 0) { /* we assume this window is contents */
					hide = false;
				}

				if (!hide) {
					Point e = w.computeSize(wHint, hHint, flushCache);
					result.x = Math.max(result.x, e.x);
					result.y += e.y + VGAP;
				}
			}

			if (wHint != SWT.DEFAULT) {
