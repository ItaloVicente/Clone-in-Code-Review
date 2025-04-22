			while (walk.next()) {
				final FileMode mode = walk.getFileMode(0);
				if (mode == FileMode.TREE)
					outw.print('0');
				outw.print(mode);
				outw.print(' ');
				outw.print(Constants.typeString(mode.getObjectType()));
