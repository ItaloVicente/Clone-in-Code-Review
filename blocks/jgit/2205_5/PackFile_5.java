		ObjectLoader large(PackFile pack
			Delta d = this;
			while (d.next != null)
				d = d.next;
			return d.newLargeLoader(pack
		}
