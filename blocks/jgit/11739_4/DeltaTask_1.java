		Block(int threads
				DeltaCache dc
				ObjectToPack[] list
			this.tasks = new ArrayList<DeltaTask>(threads);
			this.config = config;
			this.templateReader = reader;
			this.dc = dc;
			this.pm = pm;
			this.list = list;
			this.beginIndex = begin;
			this.endIndex = end;
		}
