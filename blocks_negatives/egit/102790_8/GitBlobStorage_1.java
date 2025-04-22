			switch (workingTreeOptions.getAutoCRLF()) {
			case INPUT:
			case FALSE:
				return objectInputStream;
			case TRUE:
			default:
				return new AutoCRLFInputStream(objectInputStream, true);
