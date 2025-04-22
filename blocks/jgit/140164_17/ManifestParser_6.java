			break;
		case "remove-project": {
			String name2 = attributes.getValue("name");
			projects.removeIf((p) -> p.getName().equals(name2));
			break;
		}
		default:
			break;
