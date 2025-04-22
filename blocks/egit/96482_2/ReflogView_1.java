				if (element instanceof ReflogEntry) {
					final ReflogEntry entry = (ReflogEntry) element;
					final PersonIdent who = entry.getWho();
					return dateFormatter.formatDate(who);
				}
				return null;
