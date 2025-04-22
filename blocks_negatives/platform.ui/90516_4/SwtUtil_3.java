        Monitor[] monitors = display.getMonitors();

        for (int idx = 0; idx < monitors.length; idx++) {
            Monitor mon = monitors[idx];

            if (mon.getClientArea().intersects(someRectangle)) {
