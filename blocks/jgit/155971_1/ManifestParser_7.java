			}
			break;
		case "default":
			defaultRemote = attributes.getValue("remote");
			defaultRevision = attributes.getValue("revision");
			break;
		case "copyfile":
			if (currentProject == null) {
