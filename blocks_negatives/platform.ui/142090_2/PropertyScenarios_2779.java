                return negated((Boolean) targetObject);
            }
        };

        getDbc().bindValue(checkbox1Selected,
                checkbox2Selected,new UpdateValueStrategy().setConverter(negatingConverter),
                new UpdateValueStrategy().setConverter(negatingConverter));


        getDbc().bindValue(SWTObservables.observeEnabled(text1), checkbox1Selected);
        getDbc().bindValue(SWTObservables.observeEnabled(text2), checkbox2Selected);

        assertEquals(true, text1.getEnabled());
        assertEquals(false, text2.getEnabled());
        assertEquals(true, checkbox1.getSelection());
        setButtonSelectionWithEvents(checkbox1, false);
        assertEquals(false, text1.getEnabled());
        assertEquals(true, text2.getEnabled());
        assertEquals(true, checkbox2.getSelection());
        setButtonSelectionWithEvents(checkbox2, false);
        assertEquals(true, text1.getEnabled());
        assertEquals(false, text2.getEnabled());
        assertEquals(true, checkbox1.getSelection());
    }

    @Test
