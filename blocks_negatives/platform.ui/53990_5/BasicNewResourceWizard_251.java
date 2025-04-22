                window.getShell().getDisplay().asyncExec(new Runnable() {
                    @Override
					public void run() {
                        finalTarget.selectReveal(selection);
                    }
                });
