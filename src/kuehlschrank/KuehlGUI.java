package kuehlschrank;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.filechooser.FileFilter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.ListSelectionModel;

public class KuehlGUI extends javax.swing.JFrame {

    private KuehlBL gefrier, kuehl, obst;
    private DateTimeFormatter dtf;

    private JPopupMenu menu;
    private JButton sort, save, qs;

    private DefaultComboBoxModel dcbm;

    private File defaultFile;
    private JFileChooser choose;

    public KuehlGUI() {
        initComponents();

        dcbm = new DefaultComboBoxModel(new String[]{"Gefrierfach",
            "Kühlfach", "Obstfach"});

        cbFach.setModel(dcbm);

        gefrier = new KuehlBL();
        kuehl = new KuehlBL();
        obst = new KuehlBL();

        listOutput.setModel(gefrier);
        listOutput.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        choose = new JFileChooser();
        choose.setFileFilter(new FileFilter() {
            public boolean accept(File f) {
                if (f.canWrite() && f.isFile() && f.getAbsolutePath().endsWith(".csv")) {
                    return true;
                } else if (f.isDirectory()) {
                    return true;
                }
                return false;
            }

            public String getDescription() {
                return "CSV - Cascading Style Sheet";
            }

        });

        menu = new JPopupMenu("Kontextmenu");
        sort = new JButton("Sortieren");
        save = new JButton("Speichern");
        qs = new JButton("Mit QS nach Menge sortieren");
        menu.add(sort);
        menu.add(save);
        menu.add(qs);
        save.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    ArrayList<Lebensmittel> a = new ArrayList();
                    int[] counts = new int[3];

                    a.addAll(gefrier.getEntries());
                    counts[0] = gefrier.getSize();
                    a.addAll(kuehl.getEntries());
                    counts[1] = kuehl.getSize();
                    a.addAll(obst.getEntries());
                    counts[2] = obst.getSize();

                    if (choose.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                        try {
                            gefrier.write(choose.getSelectedFile().getAbsolutePath(), a, counts);
                        } catch (FileNotFoundException fnfe) {
                            JOptionPane.showMessageDialog(null,
                                    "Datei konnte nicht geöffnet werden.\n "
                                    + "Sind Sie berechtigt diese Datei zu bearbeiten?",
                                    "Fehler",
                                    JOptionPane.ERROR_MESSAGE);
                        } catch (IOException ioe) {
                            JOptionPane.showMessageDialog(null,
                                    ioe.getCause(),
                                    "Fehler",
                                    JOptionPane.ERROR_MESSAGE);
                        } catch (Exception exc) {
                            JOptionPane.showMessageDialog(null,
                                    exc.getCause(),
                                    "Fehler",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }

                }
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        });
        sort.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    switch ((String) cbFach.getSelectedItem()) {
                        case "Gefrierfach":
                            gefrier.sort();
                            break;
                        case "Kühlfach":
                            kuehl.sort();
                            break;
                        case "Obstfach":
                            obst.sort();
                            break;
                    }
                    listOutput.repaint();
                }
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        });
        
        qs.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    switch ((String) cbFach.getSelectedItem()) {
                        case "Gefrierfach":
                            gefrier.sortWithQSandComparable();
                            break;
                        case "Kühlfach":
                            kuehl.sortWithQSandComparable();
                            break;
                        case "Obstfach":
                            obst.sortWithQSandComparable();
                            break;
                    }
                    listOutput.repaint();
                }
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        });

        try {
            if (choose.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                ArrayList<Lebensmittel>[] lists = gefrier.read(choose.getSelectedFile().getAbsolutePath());
                gefrier = new KuehlBL(lists[0]);
                kuehl = new KuehlBL(lists[1]);
                obst = new KuehlBL(lists[2]);
                listOutput.setModel(gefrier);
                listOutput.repaint();
            }

        } catch (FileNotFoundException fnfe) {
            System.out.println(fnfe.getCause());
            JOptionPane.showMessageDialog(this, "Datei konnte nicht gefunden werden.",
                    "Fehler", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ioe) {
            System.out.println(ioe.getCause());
            JOptionPane.showMessageDialog(this, "Datei konnte nicht bearbeitet werden.",
                    "Fehler", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            System.out.println(e.getCause());
            JOptionPane.showMessageDialog(this, "Zugriff auf Datei konnte nicht hergestellt werden.",
                    "Fehler", JOptionPane.ERROR_MESSAGE);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        rbTypes = new javax.swing.ButtonGroup();
        plOutput = new javax.swing.JPanel();
        cbFach = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        listOutput = new javax.swing.JList();
        plInput = new javax.swing.JPanel();
        rbMilch = new javax.swing.JRadioButton();
        rbFleisch = new javax.swing.JRadioButton();
        rbApfel = new javax.swing.JRadioButton();
        lbHersteller = new javax.swing.JLabel();
        lbAblaufdatum = new javax.swing.JLabel();
        lbMenge = new javax.swing.JLabel();
        tfHersteller = new javax.swing.JTextField();
        tfAblaufdatum = new javax.swing.JTextField();
        tfMenge = new javax.swing.JTextField();
        onAdd = new javax.swing.JButton();
        onDelete = new javax.swing.JButton();
        onAbgelaufene = new javax.swing.JButton();
        onAll = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Kühlschrank");
        getContentPane().setLayout(new java.awt.GridLayout(1, 2));

        plOutput.setLayout(new java.awt.BorderLayout());

        cbFach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFachActionPerformed(evt);
            }
        });
        plOutput.add(cbFach, java.awt.BorderLayout.NORTH);

        listOutput.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listOutputMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(listOutput);

        plOutput.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        getContentPane().add(plOutput);

        plInput.setLayout(new java.awt.GridBagLayout());

        rbTypes.add(rbMilch);
        rbMilch.setSelected(true);
        rbMilch.setText("Milch");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        plInput.add(rbMilch, gridBagConstraints);

        rbTypes.add(rbFleisch);
        rbFleisch.setText("Fleisch");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        plInput.add(rbFleisch, gridBagConstraints);

        rbTypes.add(rbApfel);
        rbApfel.setText("Apfel");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        plInput.add(rbApfel, gridBagConstraints);

        lbHersteller.setText("Hersteller");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        plInput.add(lbHersteller, gridBagConstraints);

        lbAblaufdatum.setText("Ablaufdatum");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        plInput.add(lbAblaufdatum, gridBagConstraints);

        lbMenge.setText("Menge (kg)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        plInput.add(lbMenge, gridBagConstraints);

        tfHersteller.setColumns(15);
        tfHersteller.setText("Stainzer");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        plInput.add(tfHersteller, gridBagConstraints);

        tfAblaufdatum.setColumns(15);
        tfAblaufdatum.setText("10.03.2018");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        plInput.add(tfAblaufdatum, gridBagConstraints);

        tfMenge.setColumns(15);
        tfMenge.setText("1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        plInput.add(tfMenge, gridBagConstraints);

        onAdd.setText("Hinzufügen");
        onAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onAddActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        plInput.add(onAdd, gridBagConstraints);

        onDelete.setText("Löschen");
        onDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onDeleteActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 40, 0);
        plInput.add(onDelete, gridBagConstraints);

        onAbgelaufene.setText("Zeige nur Abgelaufene");
        onAbgelaufene.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onAbgelaufeneActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        plInput.add(onAbgelaufene, gridBagConstraints);

        onAll.setText("Zeige alle");
        onAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onAllActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        plInput.add(onAll, gridBagConstraints);

        getContentPane().add(plInput);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void cbFachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFachActionPerformed
        switch ((String) cbFach.getSelectedItem()) {
            case "Gefrierfach":
                listOutput.setModel(gefrier);
                break;
            case "Kühlfach":
                listOutput.setModel(kuehl);
                break;
            case "Obstfach":
                listOutput.setModel(obst);
                break;
        }
    }//GEN-LAST:event_cbFachActionPerformed

    private void onAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onAddActionPerformed
        if (!(tfHersteller.getText().equals("") || tfAblaufdatum.getText().equals("") || tfMenge.getText().equals(""))) {
            try {
                String[] date = tfAblaufdatum.getText().trim().split("\\.");
                int day, month, year;
                day = Integer.parseInt(date[0]);
                month = Integer.parseInt(date[1]);
                year = Integer.parseInt(date[2]);

                LocalDate ld = LocalDate.of(year, month, day);
                double menge = Double.parseDouble(tfMenge.getText().trim());

                Lebensmittel l = null;

                if (rbMilch.isSelected()) {
                    l = new Milch(ld, tfHersteller.getText(), menge);
                } else if (rbFleisch.isSelected()) {
                    l = new Fleisch(ld, tfHersteller.getText(), menge);
                } else if (rbApfel.isSelected()) {
                    l = new Apfel(ld, tfHersteller.getText(), menge);
                }

                switch ((String) cbFach.getSelectedItem()) {
                    case "Gefrierfach":
                        gefrier.add(l);
                        break;
                    case "Kühlfach":
                        kuehl.add(l);
                        break;
                    case "Obstfach":
                        obst.add(l);
                        break;
                }

            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Format des Datums oder der Menge wird nicht akzeptiert.\nTT.MM.YYYY", "Fehler", JOptionPane.ERROR_MESSAGE);
                System.out.println(nfe.getCause());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getCause(), "Fehler", JOptionPane.ERROR_MESSAGE);
            } finally {
                listOutput.repaint();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Ein Eingabefeld ist leer", "Fehler", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_onAddActionPerformed

    private void onDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onDeleteActionPerformed
        int i = listOutput.getSelectedIndex();
        if (i != -1) {
            switch ((String) cbFach.getSelectedItem()) {
                case "Gefrierfach":
                    gefrier.delete(i);
                    break;
                case "Kühlfach":
                    kuehl.delete(i);
                    break;
                case "Obstfach":
                    obst.delete(i);
                    break;
            }
            listOutput.repaint();
        } else {
            JOptionPane.showMessageDialog(this, "Kein Lebensmittel ausgewählt.", "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_onDeleteActionPerformed

    private void changeState(int n) {
        gefrier.setState(n);
        kuehl.setState(n);
        obst.setState(n);
        listOutput.repaint();
    }

    private void onAbgelaufeneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onAbgelaufeneActionPerformed
        gefrier.setState(1);
        kuehl.setState(1);
        obst.setState(1);
        listOutput.repaint();
    }//GEN-LAST:event_onAbgelaufeneActionPerformed

    private void onAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onAllActionPerformed
        gefrier.setState(0);
        kuehl.setState(0);
        obst.setState(0);
        listOutput.repaint();
    }//GEN-LAST:event_onAllActionPerformed

    private void listOutputMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listOutputMouseClicked
        if (evt.getButton() == MouseEvent.BUTTON3) {
            menu.setLocation(evt.getXOnScreen(), evt.getYOnScreen());
            menu.setVisible(true);
        } else if (evt.getButton() == MouseEvent.BUTTON1) {
            menu.setVisible(false);
        }
    }//GEN-LAST:event_listOutputMouseClicked

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(KuehlGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KuehlGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KuehlGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KuehlGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new KuehlGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbFach;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbAblaufdatum;
    private javax.swing.JLabel lbHersteller;
    private javax.swing.JLabel lbMenge;
    private javax.swing.JList listOutput;
    private javax.swing.JButton onAbgelaufene;
    private javax.swing.JButton onAdd;
    private javax.swing.JButton onAll;
    private javax.swing.JButton onDelete;
    private javax.swing.JPanel plInput;
    private javax.swing.JPanel plOutput;
    private javax.swing.JRadioButton rbApfel;
    private javax.swing.JRadioButton rbFleisch;
    private javax.swing.JRadioButton rbMilch;
    private javax.swing.ButtonGroup rbTypes;
    private javax.swing.JTextField tfAblaufdatum;
    private javax.swing.JTextField tfHersteller;
    private javax.swing.JTextField tfMenge;
    // End of variables declaration//GEN-END:variables
}
