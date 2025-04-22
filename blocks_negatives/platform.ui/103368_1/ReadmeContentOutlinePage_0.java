                    new ISelectionChangedListener() {
                        @Override
						public void selectionChanged(SelectionChangedEvent event) {
                            setEnabled(!event.getSelection().isEmpty());
                        }
                    });
