			command.call();
		} else {
			ListTagCommand command = git.tagList();
			List<RevTag> list = command.call();
			for (RevTag revTag : list) {
				out.println(revTag.getTagName());
			}
		}
