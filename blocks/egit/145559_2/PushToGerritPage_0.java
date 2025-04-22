			List<Ref> refs = repository.getRefDatabase()
					.getRefsByPrefix(Constants.R_REMOTES);
			for (Ref ref : refs) {
				String refName = ref.getName()
						.substring(Constants.R_REMOTES.length());
				int slashIndex = refName.indexOf('/');
				if (slashIndex > 0 && slashIndex < refName.length() - 1) {
					knownRemoteRefs.add(refName.substring(slashIndex + 1));
