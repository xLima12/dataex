package br.com.codenoir.view;

import br.com.codenoir.controller.ImportFileController;
import br.com.codenoir.exceptions.ExceptionHandler;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class WindowImportFile extends JFrame {

    private JLabel lbInputFile;
    private JLabel lbDestination;
    private JLabel lbDestinationValue;
    private JLabel lbLines;
    private JLabel lbLinesValue;
    private JLabel lbUnits;
    private JLabel lbUnitsValue;
    private JButton btnGenerate;
    private JTextArea txtArea;

    public WindowImportFile() {

        setTitle("Gerador de supernota");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 380);
        setLocationRelativeTo(null);

        lbInputFile = new JLabel("Selecione o arquivo");

        lbDestination = new JLabel("Filial destino:");
        lbDestinationValue = new JLabel();

        lbLines = new JLabel("Total de linhas:");
        lbLinesValue = new JLabel();

        lbUnits = new JLabel("Total de unidades:");
        lbUnitsValue = new JLabel();

        btnGenerate = new JButton("Procurar");
        btnGenerate.setPreferredSize(new Dimension(100, 35));

        txtArea = new JTextArea(10, 30);
        txtArea.setLineWrap(true);
        txtArea.setWrapStyleWord(true);
        txtArea.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelTop.add(lbInputFile);
        panelTop.add(btnGenerate);

        JPanel panelDestination = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelDestination.add(lbDestination);
        panelDestination.add(lbDestinationValue);
        panelDestination.add(lbLines);
        panelDestination.add(lbLinesValue);
        panelDestination.add(lbUnits);
        panelDestination.add(lbUnitsValue);

        JScrollPane scroll = new JScrollPane(txtArea);
        scroll.setPreferredSize(new Dimension(550, 200));
        scroll.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel panelTxtArea = new JPanel();
        panelTxtArea.setLayout(new BoxLayout(panelTxtArea, BoxLayout.X_AXIS));
        panelTxtArea.add(Box.createHorizontalGlue());
        panelTxtArea.add(scroll);
        panelTxtArea.add(Box.createHorizontalGlue());
        panelTxtArea.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel panelInfo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelInfo.add(panelDestination, BorderLayout.NORTH);
        panelInfo.add(panelTxtArea, BorderLayout.CENTER);

        JPanel panelMain = new JPanel(new BorderLayout());
        panelMain.add(panelTop, BorderLayout.NORTH);
        panelMain.add(panelInfo, BorderLayout.CENTER);

        getContentPane().add(panelMain);

        btnGenerate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cleanLabels();

                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showOpenDialog(null);
                if(option != JFileChooser.APPROVE_OPTION) {
                    JOptionPane.showMessageDialog(null, "Nenhum arquivo selecionado.");
                }

                File file = fileChooser.getSelectedFile();

                updateLabels(file.getAbsolutePath());

            }
        });

    }

    public void updateLabels(String pathFile) {
        ImportFileController importFileController = new ImportFileController();
        try {
            importFileController.generatedXml(pathFile);
        } catch (Exception ex) {
            ExceptionHandler.handle(ex);
            return;
        }

        txtArea.setText(importFileController.getXmlComplete());
        lbDestinationValue.setText(importFileController.getFullBranchDestination());
        lbLinesValue.setText(String.valueOf(importFileController.getCount()));
        lbUnitsValue.setText(String.valueOf(importFileController.getUnits()));
    }

    public void cleanLabels() {
        lbDestinationValue.setText("");
        lbLinesValue.setText(String.valueOf(0));
        lbUnitsValue.setText(String.valueOf(0));
    }
}
