			Object obj = itr.next();
			if (obj instanceof IResource) {
				IResource res = (IResource) obj;
				if ((res.getType() & resourceTypes) == res.getType()) {
					resources.add(res);
				}
