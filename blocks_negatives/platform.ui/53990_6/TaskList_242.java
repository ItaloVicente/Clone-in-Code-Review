                new Runnable() {
                    @Override
					public void run() {
                        viewer.getControl().setRedraw(false);
                        viewer.refresh(false);
                        viewer.getControl().setRedraw(true);
                        updateStatusMessage();
                        updateTitle();
                    }
                });
