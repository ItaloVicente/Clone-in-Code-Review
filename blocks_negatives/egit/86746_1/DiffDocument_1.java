				DiffStyleRange key = new DiffStyleRange();
				key.start = currentOffset;
				key.length = 0;
				currIdx = Arrays.binarySearch(DiffDocument.this.ranges, key,
						(a, b) -> {
							if (a.start > b.start + b.length) {
								return 1;
							}
							if (b.start > a.start + a.length) {
								return -1;
							}
							return 0;
						});
