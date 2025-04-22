		if (System.out.checkError()) {
			System.err.println("An unknown I/O error occurs on stdout");
			System.exit(1);
		}
		if (System.err.checkError()) {
			System.exit(1);
		}
