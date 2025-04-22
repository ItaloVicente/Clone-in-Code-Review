			growIfFull();
			p = -(p + 1);
			if (p < cnt)
				System.arraycopy(notes, p, notes, p + 1, cnt - p);
			notes[p] = new Note(noteOn, noteData.copy());
			cnt++;
			return this;
