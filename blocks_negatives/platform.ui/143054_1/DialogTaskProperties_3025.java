        int priority = getPriority();
        initialAttributes.put(IMarker.PRIORITY, priority);
        if (priority == IMarker.PRIORITY_HIGH) {
            priorityCombo.select(priorityCombo.indexOf(PRIORITY_HIGH));
        } else if (priority == IMarker.PRIORITY_LOW) {
            priorityCombo.select(priorityCombo.indexOf(PRIORITY_LOW));
        } else {
            priorityCombo.select(priorityCombo.indexOf(PRIORITY_NORMAL));
        }
        boolean completed = getCompleted();
        initialAttributes.put(IMarker.DONE, completed ? Boolean.TRUE : Boolean.FALSE);
        completedCheckbox.setSelection(completed);
        super.updateDialogForNewMarker();
    }

    @Override
