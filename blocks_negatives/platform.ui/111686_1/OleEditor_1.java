                    changeRunnable = new Runnable() {
                        @Override
						public void run() {
                            sourceDeleted = true;
                            getSite().getPage().closeEditor(OleEditor.this,
                                    true);
                        }
                    };
