				if (time != null) {
					String refName = new String(rev
					Ref resolved = getRefDatabase().getRef(refName);
					if (resolved == null)
						return null;
					ref = resolveReflog(rw
					i = m;
				} else
					i = m - 1;
