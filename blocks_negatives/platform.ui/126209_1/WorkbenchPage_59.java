                new Runnable() {
                    @Override
					public void run() {
                        try {
					result[0] = busyOpenEditor(input, editorID, activate, matchFlags, editorState,
							notify);
                        } catch (PartInitException e) {
                            ex[0] = e;
                        }
                    }
                });
