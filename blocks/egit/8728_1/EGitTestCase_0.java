			StringBuilder s = new StringBuilder("Failed waiting for workspace refresh.");
			if (lastEvent != null) {
				s.append(" Last event was: ").append(lastEvent);
				s.append(", resource ").append(lastEvent.getResource());
				s.append(", type ").append(lastEvent.getType());
			} else {
				s.append(" There was no last IResourceChangeEvent.");
			}
			return s.toString();
