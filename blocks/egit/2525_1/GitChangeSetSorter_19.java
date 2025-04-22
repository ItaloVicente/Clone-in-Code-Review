		if (e1 instanceof GitModelBlob && !(e2 instanceof GitModelBlob))
			return 1;

		if (e2 instanceof GitModelBlob && !(e1 instanceof GitModelBlob))
			return -1;

