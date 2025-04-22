			for (IResource res : in.getItems()) {
				b.append(res.getFullPath());
				if (res.getType() == IResource.FOLDER)
					b.append('/');
				if (b.length() > 100) {
					b.append("...  "); //$NON-NLS-1$
					break;
				}
				b.append(", "); //$NON-NLS-1$
