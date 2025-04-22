        	URI resolvedURI = pathVariableManager.resolveURI(URIUtil.toURI(value));
        	IPath resolvedValue = URIUtil.toPath(resolvedURI);
            IFileInfo file = IDEResourceInfoUtils.getFileInfo(resolvedValue);
            if (!isBuiltInVariable(varName))
            	cell.setImage(file.exists() ? (file.isDirectory() ? FOLDER_IMG : FILE_IMG) : imageUnkown);
            else
            	cell.setImage(BUILTIN_IMG);
