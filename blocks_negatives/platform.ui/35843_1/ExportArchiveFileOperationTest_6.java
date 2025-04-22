		for (int i = 0; i < members.length; i++){
			if (isDirectory(members[i])){
				IResource[] folderMembers = ((IFolder)members[i]).members();
				for (int k = 0; k < folderMembers.length; k++){
					if (isFile(folderMembers[k])){
						resources.add(folderMembers[k]);
