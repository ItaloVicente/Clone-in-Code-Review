				getEncoding())) {

			os.write(hobject);
			os.write(' ');
			getObjectId().copyTo(os);
			os.write('\n');
