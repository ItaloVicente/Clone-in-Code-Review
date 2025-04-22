			os.write(htype);
			os.write(' ');
			os.write(Constants
					.encodeASCII(Constants.typeString(getObjectType())));
			os.write('\n');

			os.write(htag);
			os.write(' ');
