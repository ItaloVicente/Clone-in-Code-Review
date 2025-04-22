				if (estimateSize(noteOn, or) < LeafBucket.MAX_SIZE) {
					InMemoryNoteBucket r = new LeafBucket(prefixLen);
					for (Iterator<Note> i = iterator(noteOn, or); i.hasNext();)
						r = r.append(i.next());
					r.nonNotes = nonNotes;
					return r;
				}

				return this;
