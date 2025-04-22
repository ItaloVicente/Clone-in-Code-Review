		TestCSSEngine engine = new TestCSSEngine();
		engine.setElementProvider((element, engine1) -> {
			Element e = new TestElement("E", engine1);
			e.setAttribute("a", element.toString());
			return e;
		});
