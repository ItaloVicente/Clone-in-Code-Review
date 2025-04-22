		synchronized (keptjobinfos) {
			if (keptjobinfos.remove(jte)) {
				removed = true;
				finishedTime.remove(jte);
				disposeAction(jte);

				JobTreeElement jtes[] = getKeptElements();
				for (JobTreeElement jobTreeElement : jtes) {
					JobTreeElement parent = jobTreeElement.getParent();
					if (parent != null) {
						if (parent == jte || parent.getParent() == jte) {
							if (keptjobinfos.remove(jobTreeElement)) {
								disposeAction(jobTreeElement);
							}
							finishedTime.remove(jobTreeElement);
