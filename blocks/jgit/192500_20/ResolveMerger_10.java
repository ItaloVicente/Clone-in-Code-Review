		if(tw instanceof RenameProcessingTreeWalk && ((RenameProcessingTreeWalk) tw).isTerminated()){
			cleanUp();
			return false;
		}


		if (detectRenames) {
			boolean success = processRenames(baseTree
			if (!success) {
				cleanUp();
				return false;
			}
		}

