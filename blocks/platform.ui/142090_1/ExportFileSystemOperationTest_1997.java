		super.doTearDown();
		File root = new File(localDirectory);
		if (root.exists()){
			FileSystemHelper.clear(root);
		}
		try {
			project.delete(true, true, null);
		} catch (CoreException e) {
			fail(e.toString());
		}
		finally{
			project = null;
			localDirectory = null;
		}
