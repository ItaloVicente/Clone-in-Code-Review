		if (keptjobinfos.remove(jte)) {
			removed = true;
			finishedTime.remove(jte);
			disposeAction(jte);

			JobTreeElement jtes[] = (JobTreeElement[]) keptjobinfos.toArray(new JobTreeElement[keptjobinfos.size()]);
			for (int i = 0; i < jtes.length; i++) {
				JobTreeElement parent = (JobTreeElement) jtes[i].getParent();
				if (parent != null) {
					if (parent == jte || parent.getParent() == jte) {
						if (keptjobinfos.remove(jtes[i])) {
							disposeAction(jtes[i]);
