        FrameworkListener listener = event -> {
            if (event.getType() == FrameworkEvent.PACKAGES_REFRESHED) {
                synchronized (flag) {
                    flag[0] = true;
                    flag.notifyAll();
                }
            }
        };

        wiring.refreshBundles(asList(bundles), listener);

