        boolean saveNeeded = false;
        if (activeEditor != null)
            saveNeeded = activeEditor.isDirty();
        if (saveNeeded)
        else
    }
