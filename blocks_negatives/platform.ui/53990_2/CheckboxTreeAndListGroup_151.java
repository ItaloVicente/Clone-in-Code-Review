                new Runnable() {
                    @Override
					public void run() {
                        setTreeChecked(root, selection);
                        listViewer.setAllChecked(selection);
                    }
                });
