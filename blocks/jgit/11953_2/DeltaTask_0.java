		int cost() {
			int d = (int) (totalWeight / bytesPerUnit);
			if (totalWeight % bytesPerUnit != 0)
				d++;
			return d;
		}

