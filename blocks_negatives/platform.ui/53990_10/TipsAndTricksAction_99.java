                BusyIndicator.showWhile(shell.getDisplay(), new Runnable() {
                    @Override
					public void run() {
                        workbenchWindow.getWorkbench().getHelpSystem()
								.displayHelpResource(href);
                    }
                });
