		if ((type == MODIFY || type == RENAME) && !oldMode.equals(newMode)) {
			oldMode.copyTo(o);
			o.write('\n');

			newMode.copyTo(o);
			o.write('\n');
		}

