
        Adventure adventure = SampleData.WINTER_HOLIDAY;
        Text text = new Text(getComposite(), SWT.BORDER);

        IObservableValue descriptionObservable = BeansObservables.observeValue(adventure, "description");
        IObservableValue nameObservable = BeansObservables.observeValue(adventure, "name");
        AggregateObservableValue customObservable_comma = new AggregateObservableValue(new IObservableValue[] {
                descriptionObservable, nameObservable }, ",);
-
-        getDbc().bindValue(SWTObservables.observeText(text, SWT.Modify), customObservable_comma);
-        // spinEventLoop(1);
-        // Make sure that the description on the model match the widget
-        assertEquals(adventure.getDescription() + , + adventure.getName(), text.getText());
-
-        // Change the widget to newDescription,newName and ensure the model is
-        // updated
-        text.setText(newDescription,newName");
        assertEquals("newDescription", adventure.getDescription());
        assertEquals("newName", adventure.getName());

        adventure.setDescription("newDescription_0");
        adventure.setName("newName_0");
        assertEquals("newDescription_0,newName_0", text.getText());

        text.setText("newDescription_1");
        assertEquals("newDescription_1", adventure.getDescription());
        assertEquals(null, adventure.getName());


    }
