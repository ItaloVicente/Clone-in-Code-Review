				if (patchSetNumber == null) {
					return o.getPatchSetNumber() != null ? -1 : 0;
				} else if (o.getPatchSetNumber() == null) {
					return 1;
				}
				changeDiff = this.patchSetNumber
