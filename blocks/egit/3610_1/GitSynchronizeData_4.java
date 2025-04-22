			String realName;
			Ref ref;
			try {
				ref = repo.getRef(rev);
			} catch (IOException e) {
				ref = null;
			}
			if (ref != null && ref.isSymbolic())
				realName = ref.getTarget().getName();
