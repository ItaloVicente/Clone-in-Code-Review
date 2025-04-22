					+ format(ent.getNewId())));
			if (oldMode.equals(newMode)) {
				o.write(' ');
				newMode.copyTo(o);
			}
			o.write('\n');
