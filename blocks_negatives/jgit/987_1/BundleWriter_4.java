		for (final Map.Entry<String, ObjectId> e : include.entrySet()) {
			e.getValue().copyTo(tmp, w);
			w.write(' ');
			w.write(e.getKey());
			w.write('\n');
		}

		w.write('\n');
		w.flush();
		packWriter.writePack(os);
