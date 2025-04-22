		MWindow win = getWindowFor(element);
		if (!(win instanceof MTrimmedWindow)) {
			return;
		}

		MTrimmedWindow window = (MTrimmedWindow) win;
