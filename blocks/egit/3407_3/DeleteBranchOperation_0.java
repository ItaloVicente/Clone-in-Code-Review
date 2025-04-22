					StringBuilder names = new StringBuilder();
					for (Iterator<Ref> it = branches.iterator(); it.hasNext(); ) {
						Ref ref = it.next();
						names.append(ref.getName());
						if (it.hasNext())
							names.append(", "); //$NON-NLS-1$
					}
