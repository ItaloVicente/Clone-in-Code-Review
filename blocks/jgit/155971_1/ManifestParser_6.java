			currentProject = new RepoProject(attributes.getValue("name")
					attributes.getValue("path")
					attributes.getValue("revision")
					attributes.getValue("remote")
					attributes.getValue("groups"));
			currentProject
					.setRecommendShallow(attributes.getValue("clone-depth"));
			break;
		case "remote":
			String alias = attributes.getValue("alias");
			String fetch = attributes.getValue("fetch");
			String revision = attributes.getValue("revision");
