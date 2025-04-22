			command.call();
		} else {
			for (Ref tagRef : git.tagList().call()) {
				System.out.println(Repository.shortenRefName(tagRef.getName()));
			}
		}
