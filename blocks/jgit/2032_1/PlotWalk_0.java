
		int kind(Ref r) {
			if (r.getName().startsWith(R_TAGS))
				return 0;
			if (r.getName().startsWith(R_HEADS))
				return 1;
			if (r.getName().startsWith(R_REMOTES))
				return 2;
			return 3;
		}
