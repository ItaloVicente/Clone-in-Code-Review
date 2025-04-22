				if (path == null && Integer.MAX_VALUE < size) {
					LargeObjectException.ExceedsByteArrayLimit e;
					e = new LargeObjectException.ExceedsByteArrayLimit();
					e.setObjectId(id);
					throw e;
				}
