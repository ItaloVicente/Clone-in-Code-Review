                String variableName = entry.getKey();
                IPath variableValue = entry.getValue();
                if (!isBuiltInVariable(variableName))
                    pathVariableManager.setURIValue(variableName, URIUtil.toURI(variableValue));
            }
            initTemporaryState();

            return true;
        } catch (CoreException ce) {
            ErrorDialog.openError(shell, null, null, ce.getStatus());
        }
        return false;
    }

    /**
     * Removes the currently selected variables.
     */
    private void removeSelectedVariables() {
