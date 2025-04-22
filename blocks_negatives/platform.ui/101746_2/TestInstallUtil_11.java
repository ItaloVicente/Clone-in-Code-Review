        FrameworkListener listener = new FrameworkListener() {
            @Override
			public void frameworkEvent(FrameworkEvent event) {
                if (event.getType() == FrameworkEvent.PACKAGES_REFRESHED) {
					synchronized (flag) {
                        flag[0] = true;
                        flag.notifyAll();
                    }
