			outw.print(mode);
			outw.format(" %6d"
			outw.print(' ');
			outw.print(fmt.format(mtime));
			outw.print(' ');
			outw.print(ent.getObjectId().name());
			outw.print(' ');
			outw.print(stage);
			outw.print('\t');
			outw.print(ent.getPathString());
			outw.println();
