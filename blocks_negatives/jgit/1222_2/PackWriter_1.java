			try {
				if (loadSize(eo))
					list[cnt++] = eo;
			} catch (IOException notAvailable) {
				if (!ignoreMissingUninteresting)
					throw notAvailable;
			}
