			} else {
				res.a = new RawText(aRaw);
				res.b = new RawText(bRaw);
				editList = diff(res.a, res.b);
				type = PatchType.UNIFIED;

				switch (ent.getChangeType()) {
				case RENAME:
				case COPY:
					if (!editList.isEmpty())
						formatOldNewPaths(buf, ent);
					break;
