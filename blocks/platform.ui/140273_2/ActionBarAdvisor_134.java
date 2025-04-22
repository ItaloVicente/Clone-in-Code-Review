		}
		if ((flags & FILL_COOL_BAR) != 0) {
			fillCoolBar(actionBarConfigurer.getCoolBarManager());
		}
		if ((flags & FILL_STATUS_LINE) != 0) {
			fillStatusLine(actionBarConfigurer.getStatusLineManager());
		}
	}
