
	static class MyOptionDef extends OptionDef {

		public MyOptionDef(OptionDef o) {
			super(o.usage()
					.isMultiValued());
		}

		@Override
		public String toString() {
			if (metaVar() == null)
				return "ARG";
			try {
				Field field = CLIText.class.getField(metaVar());
				String ret = field.get(CLIText.get()).toString();
				return ret;
			} catch (Exception e) {
				e.printStackTrace(System.err);
				return metaVar();
			}
		}
	}

	@Override
	protected OptionHandler createOptionHandler(OptionDef o
		if (o instanceof NamedOptionDef)
			return super.createOptionHandler(o
		else
			return super.createOptionHandler(new MyOptionDef(o)

	}
