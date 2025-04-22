			IPath location = prj.getLocation();
			if (location != null) {
				return location.toString();
			}
			URI locationURI = prj.getLocationURI();
			if (locationURI != null) {
				return locationURI.toString();
			}
			return null;
