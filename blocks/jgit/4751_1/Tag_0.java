			command.call();
		} else {
			ListTagCommand command = git.tagList();
			List<Ref> list = command.call();
			for (Ref revTag : list) {
				out.println(Repository.shortenRefName(revTag.getName()));
			}
		}
