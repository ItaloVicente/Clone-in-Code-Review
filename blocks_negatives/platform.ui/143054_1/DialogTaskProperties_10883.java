                    initialAttributes.put(IMarker.DONE, completedCheckbox.getSelection() ? Boolean.TRUE : Boolean.FALSE);
                }
                markDirty();
            }
        });
    }

    protected boolean getCompleted() {
        IMarker marker = getMarker();
        if (marker == null) {
