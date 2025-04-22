				String opt = line.substring(45);
				if (opt.startsWith(" "))
					opt = opt.substring(1);
				for (String c : opt.split(" "))
					options.add(c);
				line = line.substring(0, 45);
