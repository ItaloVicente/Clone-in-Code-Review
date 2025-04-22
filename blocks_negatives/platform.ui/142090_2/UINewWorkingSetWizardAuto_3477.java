                    Table.class);
            Table table = (Table) widgets.get(0);
            /*
             * Test initial page state
             */
            assertEquals(descriptors.length, table.getItemCount());
            assertTrue(typePage.canFlipToNextPage() == false);
            assertTrue(fWizard.canFinish() == false);
            /*
             * Test page state with page complete input
             */
            table.setSelection(descriptors.length - 1);
            table.notifyListeners(SWT.Selection, new Event());
            assertTrue(typePage.canFlipToNextPage());
            assertTrue(fWizard.canFinish() == false);

            /*
             * Check page texts
             */
