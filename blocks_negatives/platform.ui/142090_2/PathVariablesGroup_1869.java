				    IPath value = URIUtil.toPath(uri);
				    if (value != null) {
				        boolean isFile = value.toFile().isFile();
				        if ((isFile && (variableType & IResource.FILE) != 0)
				                || (isFile == false && (variableType & IResource.FOLDER) != 0)) {

				            tempPathVariables.put(varName, value);
				        }
				    }
