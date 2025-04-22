					RevTag tag = rw.parseTag(repository.resolve(tagRef.getName()));
					if (tag.getObject().name().equals(commitId)) {
						Date timestamp;
						if (tag.getTaggerIdent() != null) {
							timestamp = tag.getTaggerIdent().getWhen();
						} else {
							timestamp = null;
