			FileUtils.rename(tmpPack
			for (Map.Entry<PackExt
				File tmpExt = tmpEntry.getValue();
				tmpExt.setReadOnly();

				File realExt = nameFor(id
				try {
					FileUtils.rename(tmpExt
							StandardCopyOption.ATOMIC_MOVE);
				} catch (IOException e) {
					File newExt = new File(realExt.getParentFile()
					try {
						FileUtils.rename(tmpExt
								StandardCopyOption.ATOMIC_MOVE);
					} catch (IOException e2) {
						newExt = tmpExt;
						e = e2;
