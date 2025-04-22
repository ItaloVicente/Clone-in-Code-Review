                }

                if (location != null) {
                    if (locationMemento != null) {
                        location.setInput(editorInfo.editorInput);
                        location.restoreState(locationMemento);
                        locationMemento = null;
                    }
                    location.restoreLocation();
                }
            } catch (PartInitException e) {
            }
        }
    }

    /**
     * Return the label to display in the history drop down list.  Use the
     * history entry text if the location has not been restored yet.
     */
    String getHistoryText() {
        if (location != null) {
            String text = location.getText();
                text = historyText;
            } else {
                historyText = text;
            }
            return text;
        }
