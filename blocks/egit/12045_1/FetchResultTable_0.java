				if (e1 instanceof FileDiff && e2 instanceof FileDiff) {
					FileDiff f1 = (FileDiff) e1;
					FileDiff f2 = (FileDiff) e2;
					return f1.getPath().compareTo(f2.getPath());
				}

