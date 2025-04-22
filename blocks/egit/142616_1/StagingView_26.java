		case CONFLICTING: {
			if (!entry.getLocation().toFile().exists()) {
				rmPaths.add(entry.getPath());
			} else {
				addPaths.add(entry.getPath());
			}
			break;
		}
