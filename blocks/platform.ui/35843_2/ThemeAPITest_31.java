                && Platform.getOS().equals("win32")) {
			rgb = new RGB(50, 50, 50);
		} else if (Platform.getWS().equals("gtk")
                && Platform.getOS().equals("linux")) {
			rgb = new RGB(25, 25, 25);
		} else if (Platform.getOS().equals("linux")) {
			rgb = new RGB(75, 75, 75);
		} else {
			rgb = new RGB(0, 0, 0);
		}
