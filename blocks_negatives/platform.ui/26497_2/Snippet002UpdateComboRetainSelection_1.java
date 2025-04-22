                    List newList = new ArrayList();
                    newList.add("Chocolate");
                    newList.add("Vanilla");
                    newList.add("Mango Parfait");
                    newList.add("beef");
                    newList.add("Cheesecake");
                    newList.add(Integer.toString(++count));
                    viewModel.setChoices(newList);
                }
            });

            System.out.println(viewModel.getText());

            DataBindingContext dbc = new DataBindingContext();
            
            IObservableList list = MasterDetailObservables.detailList(BeansObservables.observeValue(viewModel, "choices"),
                    getListDetailFactory(),
                    String.class);
            dbc.bindList(SWTObservables.observeItems(combo), list); 
            dbc.bindValue(SWTObservables.observeText(combo), BeansObservables.observeValue(viewModel, "text"));
            
            shell.pack();
            shell.open();
            return shell;
        }
    }

    private static IObservableFactory getListDetailFactory() {
        return new IObservableFactory() {
            @Override
