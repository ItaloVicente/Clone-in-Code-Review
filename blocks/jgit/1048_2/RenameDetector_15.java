
			if (pm == null)
				pm = NullProgressMonitor.INSTANCE;
			findExactRenames(pm);
			findContentRenames(pm);

