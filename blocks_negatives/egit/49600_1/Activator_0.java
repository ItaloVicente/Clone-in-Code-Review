			while (projectsToScan.size() > 0) {
				IProject p;
				synchronized (projectsToScan) {
					if (projectsToScan.size() == 0)
						break;
					Iterator<IProject> i = projectsToScan.iterator();
					p = i.next();
					i.remove();
