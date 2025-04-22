			if (line.length() > 45) {
				final HashSet<String> opts = new HashSet<>();
				String opt = line.substring(45);
					opt = opt.substring(1);
					opts.add(c);
				this.line = line.substring(0, 45);
				this.options = Collections.unmodifiableSet(opts);
			} else {
				this.line = line;
				this.options = Collections.emptySet();
			}
