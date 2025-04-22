		folder = secondProject.getFolder(FOLDER);
		IFile secondtextFile = folder.getFile(FILE1);
		IFile secondtextFile2 = folder.getFile(FILE2);

		IFile[] commitables = new IFile[] { dotProject,
				textFile, textFile2, secondtextFile, secondtextFile2 };
