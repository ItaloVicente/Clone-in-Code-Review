			if (obj instanceof GitModelBlob) {
				IResource res = root.getFileForLocation(obj.getLocation());
				if (res == null)
					res = root.getFile(obj.getLocation());

				return res;
			}
