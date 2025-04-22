
				PackedRefList afterUpdate = packedRefs.updateAndGet(
						p -> p.id.equals(oldPackedList.id) ? newPackedList : p);
				if (!afterUpdate.id.equals(newPackedList.id)) {
					throw new ObjectWritingException(
							MessageFormat.format(JGitText.get().unableToWrite, name));
				}
