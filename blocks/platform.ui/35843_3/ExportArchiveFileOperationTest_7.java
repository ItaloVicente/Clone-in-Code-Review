		for (IResource member : members) {
			if (isDirectory(member)){
				IResource[] folderMembers = ((IFolder)member).members();
				for (IResource folderMember : folderMembers) {
					if (isFile(folderMember)){
						resources.add(folderMember);
