			FontData[] fixedFonts = display.getFontList(fd.getName(), false);
			if (isFixedFont(fixedFonts, fd)) {
				good.add(fd);
			}

			FontData[] scalableFonts = display.getFontList(fd.getName(), true);
			if (scalableFonts.length > 0) {
				good.add(fd);
			}
		}


		if (good.isEmpty() && fonts.length > 0) {
			good.add(fonts[0]);
		}
		else if (fonts.length == 0) {
			return null;
		}

		return good.toArray(new FontData[good.size()]);
	}


	private FontRecord createFont(String symbolicName, FontData[] fonts) {
		Display display = Display.getCurrent();
		if (display == null) {
			return null;
		}
		if (cleanOnDisplayDisposal && !displayDisposeHooked) {
			hookDisplayDispose(display);
		}

		FontData[] validData = filterData(fonts, display);
		if (validData.length == 0) {
			return null;
		}

		put(symbolicName, validData, false);
		Font newFont = new Font(display, validData);
		return new FontRecord(newFont, validData);
	}

	Font calculateDefaultFont() {
		Display current = Display.getCurrent();
		if (current == null) // can't do much without Display
			SWT.error(SWT.ERROR_THREAD_INVALID_ACCESS);
