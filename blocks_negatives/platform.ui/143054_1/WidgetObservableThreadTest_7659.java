		threadRealm.exec(new Runnable() {
			@Override
			public void run() {
				ctx = new DataBindingContext();
				ctx.bindValue(WidgetProperties.text(SWT.Modify).observe(text),
						BeanProperties.value("value")
								.observe(threadRealm, bean));
			}
