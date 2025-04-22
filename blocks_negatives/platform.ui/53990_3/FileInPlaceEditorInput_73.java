                    changeRunnable = new Runnable() {
                        @Override
						public void run() {
                            if (embeddedEditor != null) {
                                embeddedEditor.sourceDeleted();
                                embeddedEditor.getSite().getPage().closeEditor(
                                        embeddedEditor, true);
                            }
                        }
                    };
