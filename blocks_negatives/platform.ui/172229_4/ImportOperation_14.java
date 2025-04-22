			} else {
				if (targetResource.exists()) {
					if (targetResource.isLinked()) {
						targetResource.delete(true, subMonitor.split(50));
						targetResource.create(contentStream, false, subMonitor.split(50));
					} else
						targetResource.setContents(contentStream, IResource.KEEP_HISTORY, subMonitor.split(100));
