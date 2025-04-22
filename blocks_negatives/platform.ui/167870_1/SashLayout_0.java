			List<SashRect> sashList = getSashRects(relativeToHost.x, relativeToHost.y);
			if (sashList.isEmpty()) {
				if (state != State.OTHER) {
					state = State.OTHER;
					host.setCursor(host.getDisplay().getSystemCursor(SWT.CURSOR_ARROW));
				}
			} else if (sashList.size() > 1) {
				if (state != State.HOVERING_ALL) {
					state = State.HOVERING_ALL;
					host.setCursor(host.getDisplay().getSystemCursor(SWT.CURSOR_SIZEALL));
				}
			} else if (sashList.get(0).container.isHorizontal()) {
				if (state != State.HOVERING_WE) {
					state = State.HOVERING_WE;
					host.setCursor(host.getDisplay().getSystemCursor(SWT.CURSOR_SIZEWE));
				}
			} else {
				if (state != State.HOVERING_NS) {
					state = State.HOVERING_NS;
					host.setCursor(host.getDisplay().getSystemCursor(SWT.CURSOR_SIZENS));
				}
			}
