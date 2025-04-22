			}
			root.delete();
		}
		try {
			project.delete(true, true, null);
		} catch (CoreException e) {
			fail(e.toString());
		}
		finally{
			project = null;
			localDirectory = null;
			filePath = null;
		}
	}

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

			for (String emptyDirectoryName : emptyDirectoryNames) {
				IFolder folder = project.getFolder(emptyDirectoryName);
				folder.create(false, true, new NullProgressMonitor());
			}
		}
		catch(Exception e){
			fail(e.toString());
		}
