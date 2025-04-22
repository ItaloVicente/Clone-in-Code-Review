		dbc.bindValue(WidgetProperties.text(SWT.Modify).observe(title), DuplexingObservableValue
				.withDefaults(BeanProperties.value("title").observeDetail(selections), "", "<Multiple titles>"));
		dbc.bindValue(WidgetProperties.text(SWT.Modify).observe(releaseDate), DuplexingObservableValue
				.withDefaults(BeanProperties.value("releaseDate").observeDetail(selections), "", "<Multiple dates>"));
		dbc.bindValue(WidgetProperties.text(SWT.Modify).observe(director), DuplexingObservableValue
				.withDefaults(BeanProperties.value("director").observeDetail(selections), "", "<Multiple directors>"));
		dbc.bindValue(WidgetProperties.text(SWT.Modify).observe(writer), DuplexingObservableValue
				.withDefaults(BeanProperties.value("writer").observeDetail(selections), "", "<Multiple writers>"));
