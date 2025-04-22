		if ((e1 instanceof GitModelTree && e1 instanceof GitModelTree) ||
				(e1 instanceof GitModelBlob && e1 instanceof GitModelBlob))
			return super.compare(viewer, e1, e2);

		if (e1 instanceof GitModelTree && e2 instanceof GitModelCommit)
			return 1;

