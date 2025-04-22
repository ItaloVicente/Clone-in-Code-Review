			if(!status.isOK()){
				errorTable.add(status);
				ArrayList filteredFiles = new ArrayList();

				for (IFile file : files) {
					filteredFiles.add(file.getFullPath());
				}
				return filteredFiles;
			}

		}
		return new ArrayList();
	}

