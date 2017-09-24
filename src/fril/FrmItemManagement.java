/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fril;

import fril.ListItems.EditInfo;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author CuongPhan
 */
public class FrmItemManagement extends javax.swing.JFrame {
 
    /**
     * Creates new form FrmItemManagement
     */
    public FrmItemManagement() throws IOException {
        initComponents();
        
        DefaultTableModel defaultTableModelExhibition
                = new DefaultTableModel(new Object[]{" ", "Product ID", "Product Name", "Edit", "Upload", "Delete"}, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return ImageIcon.class;
                }
                return String.class;
            }
        };

        jTable_ItemManagement.setModel(defaultTableModelExhibition);            
        //TableColumn columnDeleted = jTable_ItemManagement.getColumnModel().getColumn(1);
        //jTable_ItemManagement.removeColumn(columnDeleted);
        jTable_ItemManagement.getColumnModel().getColumn(1).setMaxWidth(0);
        jTable_ItemManagement.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedColumn = jTable_ItemManagement.getSelectedColumn();
                int selectedRow = jTable_ItemManagement.getSelectedRow();
                String selectedValue = jTable_ItemManagement.getValueAt(selectedRow, 1).toString();
                switch (selectedColumn) {
                    case 3:         
                        //modify item
                        EditInfo editedInfo;
                        try {
                            editedInfo = Utility.getEditInfo(selectedValue);
                            editedInfo.strHref = "" + selectedValue;
                            FrmItem frmItem;
                            frmItem = new FrmItem(selectedValue);
                            //check whether SellingTable's existion, item's existion
                            SqliteJDBC sqliteJDBC = new SqliteJDBC();
                            if(sqliteJDBC.checkIfTableAlreadyExists()){
                                ResultSet rs = sqliteJDBC.getItemFromTable(selectedValue);
                                if(rs == null){
                                    frmItem.SetEditedProductInfo(editedInfo);
                                }
                            } else{
                                frmItem.SetEditedProductInfo(editedInfo);
                            }        
                            frmItem.loadDataToComboboxes();
                            frmItem.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            frmItem.pack();
                            frmItem.setVisible(true);
                        } catch (IOException ex) {
                            Logger.getLogger(ListItems.class.getName()).log(Level.SEVERE, null, ex);
                        }   break;
                    case 4:
                        //upload item
                        //TODO
                        if(Utility.ReUploadItem(selectedValue)){
                            JOptionPane.showMessageDialog(null, "Upload item successfully");
                        } else{
                            JOptionPane.showMessageDialog(null, "Upload item failed");
                        }   break;
                    case 5:
                        //delete item
                        int dialogResult = JOptionPane.showConfirmDialog(null, "Delete ?", "Confirm delete item", JOptionPane.YES_NO_OPTION);
                        if (dialogResult == JOptionPane.YES_OPTION) {
                            selectedValue = jTable_ItemManagement.getValueAt(selectedRow, 1).toString();
                            try {
                                Utility.deleteItem(selectedValue);
                            } catch (IOException ex) {
                                Logger.getLogger(ListItems.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }   break;
                    default:
                        break;
                }
            }

        });
        loadSellingItems();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_AddNewItem = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_ItemManagement = new javax.swing.JTable();
        btn_StartScheduler = new javax.swing.JButton();
        btn_StopScheduler = new javax.swing.JButton();
        btn_AddNewItem1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btn_AddNewItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fril/Images/add.png"))); // NOI18N
        btn_AddNewItem.setText("Add New Item");
        btn_AddNewItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AddNewItemActionPerformed(evt);
            }
        });

        jTable_ItemManagement.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable_ItemManagement);

        btn_StartScheduler.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fril/Images/start.png"))); // NOI18N
        btn_StartScheduler.setText("Start Scheduler");
        btn_StartScheduler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_StartSchedulerActionPerformed(evt);
            }
        });

        btn_StopScheduler.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fril/Images/stop.png"))); // NOI18N
        btn_StopScheduler.setText("Stop Scheduler");

        btn_AddNewItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fril/Images/add.png"))); // NOI18N
        btn_AddNewItem1.setLabel("Load Item From File");
        btn_AddNewItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AddNewItem1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 811, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btn_AddNewItem)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_AddNewItem1))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btn_StartScheduler, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_StopScheduler)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_AddNewItem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_AddNewItem1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_StartScheduler, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_StopScheduler, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_AddNewItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AddNewItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_AddNewItemActionPerformed

    private void btn_AddNewItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AddNewItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_AddNewItem1ActionPerformed

    private void btn_StartSchedulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_StartSchedulerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_StartSchedulerActionPerformed
    
    
    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_AddNewItem;
    private javax.swing.JButton btn_AddNewItem1;
    private javax.swing.JButton btn_StartScheduler;
    private javax.swing.JButton btn_StopScheduler;
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JTable jTable_ItemManagement;
    // End of variables declaration//GEN-END:variables

    public static void loadSellingItems() throws IOException {
        DefaultTableModel defaultTableModel = (DefaultTableModel) jTable_ItemManagement.getModel();
        if (null != defaultTableModel) {
            defaultTableModel.setRowCount(0);
        }
        List<ItemShortInfo> itemList = new ArrayList<>(ListItems.itemSellingList);
        for (int i = 0; i < itemList.size(); i++) {
            //get image
            BufferedImage bufferedImage = null;
            ImageIcon image = null;
            try {
                bufferedImage = ImageIO.read(new URL(itemList.get(i).strImageLink));
                image = new ImageIcon(bufferedImage);
                Image image2 = image.getImage();
                Image image3 = image2.getScaledInstance(150, 150, Image.SCALE_SMOOTH); //resize image
                ImageIcon imageicon2 = new ImageIcon(image3);
                if (null != bufferedImage) {
                    //defaultTableModel.addRow(new Object[]{imageicon2, itemList.get(i).strHref, itemList.get(i).strMediaHeading, itemList.get(i).strWaiting, "Click to edit", "Click to Upload", "Click to delete"});
                    defaultTableModel.addRow(new Object[]{imageicon2, itemList.get(i).strHref, itemList.get(i).strMediaHeading, "Click to edit", "Click to Upload", "Click to delete"});

                } else {
                    defaultTableModel.addRow(new Object[]{itemList.get(i).strHref, itemList.get(i).strMediaHeading, "Click to edit", "Click to Upload", "Click to delete"});
                }
            } catch (IOException e) {

            }
//          jTable_ItemManagement.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            int rowHeight = jTable_ItemManagement.getRowHeight();
            for (int column = 0; column < jTable_ItemManagement.getColumnCount() - 1; column++) {
                TableColumn tableColumn = jTable_ItemManagement.getColumnModel().getColumn(column);
                int preferredWidth = tableColumn.getMinWidth();
                int maxWidth = tableColumn.getMaxWidth();

                for (int row = 0; row < jTable_ItemManagement.getRowCount(); row++) {
                    TableCellRenderer cellRenderer = jTable_ItemManagement.getCellRenderer(row, column);
                    Component c = jTable_ItemManagement.prepareRenderer(cellRenderer, row, column);
                    int width = c.getPreferredSize().width + jTable_ItemManagement.getIntercellSpacing().width;
                    preferredWidth = Math.max(preferredWidth, width);
                    rowHeight = Math.max(c.getPreferredSize().height, rowHeight);
                    //  We've exceeded the maximum width, no need to check other rows
                    if (preferredWidth >= maxWidth) {
                        preferredWidth = maxWidth;
                        break;
                    }
                }
                jTable_ItemManagement.setRowHeight(rowHeight);
                tableColumn.setPreferredWidth(preferredWidth);
            }
        }
        saveItemListToDB(itemList);
    }

    private static void saveItemListToDB(List<ItemShortInfo> itemList) {
        
    }

}
