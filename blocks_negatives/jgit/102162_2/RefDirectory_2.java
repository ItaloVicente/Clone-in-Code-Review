			}
			if (!dirty) {
				return packed;
			}

			PackedRefList result = commitPackedRefs(lck, cur, packed);

			for (String refName : refs) {
				File refFile = fileFor(refName);
				if (!fs.exists(refFile)) {
					continue;
