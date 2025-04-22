			}
	    for (Entry<String, IPath> entry : tempPathVariables.entrySet()) {
		String variableName = entry.getKey();
		IPath variableValue = entry.getValue();
		if (!isBuiltInVariable(variableName))
		    pathVariableManager.setURIValue(variableName, URIUtil.toURI(variableValue));
	    }
