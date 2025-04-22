			}

			for (Entry<String, IPath> entry : tempPathVariables.entrySet()) {
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

	private void removeSelectedVariables() {
