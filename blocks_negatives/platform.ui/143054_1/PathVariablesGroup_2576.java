            String varName = (String) selectedItem.getData();
            removedVariableNames.add(varName);
            tempPathVariables.remove(varName);
        }
        updateWidgetState();
        saveVariablesIfRequired();
    }

    private boolean canChangeSelection() {
