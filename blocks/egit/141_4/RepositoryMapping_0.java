			int matchingSegments = gLoc.matchingFirstSegments(cLoc);
			IPath remainder = gLoc.removeFirstSegments(matchingSegments);
			String device = remainder.getDevice();
			if (device == null)
				gitdirPath = remainder.toPortableString();
			else
				gitdirPath = remainder.toPortableString().substring(device.length());
