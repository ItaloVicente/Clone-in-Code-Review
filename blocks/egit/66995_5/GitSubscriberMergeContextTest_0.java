		if (!iFile2.exists()) {
			System.out.println(iFile2 + " is synchronized? " + Boolean
					.toString(iFile2.isSynchronized(IResource.DEPTH_ZERO)));
			System.out.println(TestUtils.dumpThreads());
			System.out.println("***** WARNING: IFile reported as not existing");
			System.out.println(iProject + " is open? "
					+ Boolean.toString(iProject.isOpen()));
			System.out.println(file2.getPath() + " exists? "
					+ Boolean.toString(file2.exists()));
			System.out.println(iFile2 + " exists now? "
					+ Boolean.toString(iFile2.exists()));
			iFile2 = iProject.getFile(iFile2.getName());
			System.out.println(iFile2 + " exists now? "
					+ Boolean.toString(iFile2.exists()));
			fail(iFile2 + " reported not to exist");
		}
