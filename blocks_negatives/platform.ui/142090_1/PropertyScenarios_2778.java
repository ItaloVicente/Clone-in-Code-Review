        Button checkbox1 = new Button(getComposite(), SWT.CHECK);
        checkbox1.setSelection(false);
        Button checkbox2 = new Button(getComposite(), SWT.CHECK);
        checkbox2.setSelection(false);
        Text text1 = new Text(getComposite(), SWT.NONE);
        Text text2 = new Text(getComposite(), SWT.NONE);

        IObservableValue checkbox1Selected = SWTObservables.observeSelection(checkbox1);
        IObservableValue checkbox2Selected = SWTObservables.observeSelection(checkbox2);

        Converter negatingConverter = new Converter(boolean.class, boolean.class) {
            private Boolean negated(Boolean booleanObject) {
                return Boolean.valueOf(!booleanObject.booleanValue());
            }

            @Override
