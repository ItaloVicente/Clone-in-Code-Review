                    this.helpListener = new HelpListener() {
                        @Override
						public void helpRequested(HelpEvent event) {
                            handleHelpRequest(event);
                        }
                    };
