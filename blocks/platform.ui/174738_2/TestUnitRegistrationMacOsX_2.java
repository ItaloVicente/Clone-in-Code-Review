		InputStream inputStream = this.getClass().getResourceAsStream("lsregisterForOtherApp.txt");
		this.lsregisterDumpForOtherApp = this.convert(inputStream);
		inputStream = this.getClass().getResourceAsStream("lsregisterForOwnApp.txt");
		this.lsregisterDumpForOwnApp = this.convert(inputStream);
		inputStream = this.getClass().getResourceAsStream("lsregisterForOwnAppPlus.txt");
		this.lsregisterDumpForOwnAppPlus = this.convert(inputStream);
		inputStream = this.getClass().getResourceAsStream("lsreigsterForOwnApp_macOS_10_15_3.txt");
		this.lsregisterDumpMacOS10_15_3 = this.convert(inputStream);
