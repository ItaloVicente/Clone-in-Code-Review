		reader.close();
		if (myDeflater != null) {
			myDeflater.end();
			myDeflater = null;
		}
		instances.remove(selfRef);
