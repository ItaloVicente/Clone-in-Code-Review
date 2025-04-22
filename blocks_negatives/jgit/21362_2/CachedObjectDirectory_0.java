			AlternateHandle[] src = wrapped.myAlternates();
			alts = new AlternateHandle[src.length];
			for (int i = 0; i < alts.length; i++) {
				FileObjectDatabase s = src[i].db;
				alts[i] = new AlternateHandle(s.newCachedFileObjectDatabase());
			}
