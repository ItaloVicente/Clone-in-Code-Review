                BusyIndicator.showWhile(link.getDisplay(), new Runnable() {
                    @Override
					public void run() {
                        doOpenExternal();
                    }
                });
