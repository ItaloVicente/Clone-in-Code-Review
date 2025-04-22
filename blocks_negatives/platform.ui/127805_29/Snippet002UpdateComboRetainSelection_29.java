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

            IObservableList list = MasterDetailObservables.detailList(BeanProperties.value(viewModel.getClass(), "choices").observe(
            		viewModel),
                    getListDetailFactory(),
                    String.class);
