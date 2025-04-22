	private void setUpData(){
		try{
			for (String directoryName : directoryNames) {
				IFolder folder = project.getFolder(directoryName);
				folder.create(false, true, new NullProgressMonitor());
				for (String fileName : fileNames) {
					IFile file = folder.getFile(fileName);
					String contents =
						directoryName + ", " + fileName;
					file.create(new ByteArrayInputStream(contents.getBytes()),
						true, new NullProgressMonitor());
				}
			}
		}
		catch(Exception e){
			fail(e.toString());
		}
	}
