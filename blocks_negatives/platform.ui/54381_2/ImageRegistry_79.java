                display.syncExec(new Runnable() {
                    @Override
					public void run() {
                        image[0] = display.getSystemImage(id);
                    }
                });
