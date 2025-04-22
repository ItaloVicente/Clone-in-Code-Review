
			if (Util.isCocoa()) {
				String arch = System.getProperty("osgi.arch");
				if (Constants.ARCH_X86.equals(arch)) {
					Map<String, String> vmArguments = new HashMap<>(1);
					vmArguments.put("d32", null);
					base.setVMArguments(vmArguments);
				}
			}
