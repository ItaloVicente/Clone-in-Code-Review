                    if (!event.top)
                        return;
                    if (combo != null) {
                            combo.setText(event.location);
                            addToHistory(event.location);
                            updateHistory();
                    }
