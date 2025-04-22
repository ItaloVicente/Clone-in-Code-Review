					if (line != null && line.startsWith(RefDirectory.SYMREF)) {
						String target = line.substring(RefDirectory.SYMREF.length());
						Ref r = refs.get(target);
						if (r == null)
							r = new ObjectIdRef.Unpeeled(Ref.Storage.NEW
						r = new SymbolicRef(Constants.HEAD
						refs.put(r.getName()
