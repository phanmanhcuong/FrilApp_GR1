/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fril;

import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author CuongPhan
 */
public class ListItems extends javax.swing.JFrame {

    private boolean bFirstTimeCmbLoad = false;
    //List<UserAccount> lstUserAccount = new List<UserAccount>();
    Timer myTimer = null;
    private static int daysRemain = 0;

    /**
     * Creates new form ListItems
     * @throws java.io.IOException
     */
    public ListItems() throws IOException {
        initComponents();
        
        //TODO
        //boolean bAccessible = GetGrant2Access(daysRemain);
        //FrmLicenseRequest frmLicenseRequest = new FrmLicenseRequest(bAccessible, daysRemain);
        //frmLicenseRequest.ShowDialog();

        //myTimer.setDelay(Utility.g_refreshPeriod);
        ActionListener refreshListView = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myTimerTick();
            }
        };
        myTimer = new Timer(Utility.g_refreshPeriod, refreshListView);
        myTimer.start();
        
        //TODO
        //addUserAccount2Combobox();
        //exhibition tab
        //DefaultTableModel defaultTableModelExhibition = (DefaultTableModel)jTable_exhibition.getModel();
        //defaultTableModelExhibition.addRow(new Object[]{" ", "Product ID", "Product Name", "Product Price", btn_modify});
        ImageIcon btn_modify = new ImageIcon("editicon.png");
        //JLabel labelIcon = new JLabel(btn_modify);
        DefaultTableModel defaultTableModelExhibition
                = new DefaultTableModel(new Object[]{" ", "Product ID", "Product Name", "Product Status", "Edit", "Delete"}, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return ImageIcon.class;
                }
                return String.class;
            }
        };

        jTable_exhibition.setModel(defaultTableModelExhibition);
        jTable_exhibition.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedColumn = jTable_exhibition.getSelectedColumn();
                int selectedRow = jTable_exhibition.getSelectedRow();
                if (selectedColumn == 0) { //image click
                    String selectedValue;
                    selectedValue = jTable_exhibition.getValueAt(selectedRow, 1).toString();
                    CommentManagement frmComment = new CommentManagement(selectedValue);
                    //TODO
                    //set myTimer stops when close form
                    frmComment.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frmComment.pack();
                    frmComment.setVisible(true);
                } else if (jTable_exhibition.getSelectedColumn() == 4) { //modify item
                    String selectedValue;
                    selectedValue = jTable_exhibition.getValueAt(selectedRow, 1).toString();
                    EditInfo editedInfo = Utility.getEditInfo(selectedValue);
                    editedInfo.strHref = "" + selectedValue;
                    AddNewItem frmAddNewItem = new AddNewItem();
                    frmAddNewItem.SetFormListItems(ListItems.this);
                    frmAddNewItem.SetEditedProductInfo(editedInfo);
                    frmAddNewItem.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frmAddNewItem.pack();
                    frmAddNewItem.setVisible(true);
                } else if (jTable_exhibition.getSelectedColumn() == 5) { //delete item
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Delete ?", "Confirm delete item", JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        String selectedValue;
                        selectedValue = jTable_exhibition.getValueAt(selectedRow, 1).toString();
                        try {
                            Utility.deleteItem(selectedValue);
                            refreshListView();
                        } catch (IOException ex) {
                            Logger.getLogger(ListItems.class.getName()).log(Level.SEVERE, null, ex);
                        }                     
                    }
                }
            }

        });

        //DefaultTableModel defaultTableModelTrading = (DefaultTableModel)jTable_trading.getModel();
        //defaultTableModelTrading.addRow(new Object[]{" ", "Product ID", "Product Name", "Product Status", "Product DeadLine", "Shipping Address", btn_delete});
        DefaultTableModel defaultTableModelTrading = new DefaultTableModel(new Object[]{" ", "Product ID", "Product Name", "Product Status", "Product DeadLine", "Shipping Address"}, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return ImageIcon.class;
                }
                return String.class;
            }
        };
        jTable_trading.setModel(defaultTableModelTrading);
        
        refreshListView();
        refreshTradingListView();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox<>();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        SP_exhibition = new javax.swing.JScrollPane();
        jTable_exhibition = new javax.swing.JTable();
        SP_trading = new javax.swing.JScrollPane();
        jTable_trading = new javax.swing.JTable();
        SP_sold = new javax.swing.JScrollPane();
        jTable_sold = new javax.swing.JTable();
        btn_AddNewItem = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        btn_ProductSchedule = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jTable_exhibition.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        SP_exhibition.setViewportView(jTable_exhibition);

        jTabbedPane1.addTab("Exhibition", SP_exhibition);

        SP_trading.setViewportView(jTable_trading);

        jTabbedPane1.addTab("During Trading", SP_trading);

        SP_sold.setViewportView(jTable_sold);

        jTabbedPane1.addTab("Sold", SP_sold);

        btn_AddNewItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fril/Images/upload.png"))); // NOI18N
        btn_AddNewItem.setText("Add new product");
        btn_AddNewItem.setDoubleBuffered(true);
        btn_AddNewItem.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn_AddNewItem.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btn_AddNewItem.setIconTextGap(5);
        btn_AddNewItem.setMargin(new java.awt.Insets(2, 0, 2, 0));
        btn_AddNewItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_AddNewItemMouseClicked(evt);
            }
        });
        btn_AddNewItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AddNewItemActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fril/Images/Settings-icon.png"))); // NOI18N
        jButton2.setText("Setting");
        jButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton2.setIconTextGap(10);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fril/Images/user-group-icon.png"))); // NOI18N
        jButton3.setText("Manage Accounts");
        jButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton3.setIconTextGap(5);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fril/Images/adduser.jpg"))); // NOI18N
        jButton4.setText("Add Account");
        jButton4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton4.setIconTextGap(5);
        jButton4.setMargin(new java.awt.Insets(2, 2, 2, 14));

        btn_ProductSchedule.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fril/Images/inventory_icon.jpg"))); // NOI18N
        btn_ProductSchedule.setText("Product Schedule");
        btn_ProductSchedule.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn_ProductSchedule.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btn_ProductSchedule.setIconTextGap(5);
        btn_ProductSchedule.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_ProductScheduleMouseClicked(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fril/Images/User-icon.png"))); // NOI18N
        jLabel1.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 877, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btn_AddNewItem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_ProductSchedule)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5)
                .addComponent(jTabbedPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_ProductSchedule, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4))
                    .addComponent(btn_AddNewItem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void btn_AddNewItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AddNewItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_AddNewItemActionPerformed

    private void btn_AddNewItemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_AddNewItemMouseClicked
        // TODO add your handling code here:
        AddNewItem frmAddNewItem = new AddNewItem();
        frmAddNewItem.SetFormListItems(this);

        frmAddNewItem.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frmAddNewItem.pack();
        frmAddNewItem.setVisible(true);
    }//GEN-LAST:event_btn_AddNewItemMouseClicked

    private void btn_ProductScheduleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ProductScheduleMouseClicked
        // TODO add your handling code here:
//        FrmItemManagement formItemManagement = new FrmItemManagement();
//        formItemManagement.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//        formItemManagement.pack();
//        formItemManagement.setVisible(true);
    }//GEN-LAST:event_btn_ProductScheduleMouseClicked

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane SP_exhibition;
    private javax.swing.JScrollPane SP_sold;
    private javax.swing.JScrollPane SP_trading;
    private javax.swing.JButton btn_AddNewItem;
    private javax.swing.JButton btn_ProductSchedule;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private static javax.swing.JTable jTable_exhibition;
    private javax.swing.JTable jTable_sold;
    private static javax.swing.JTable jTable_trading;
    // End of variables declaration//GEN-END:variables

    public static void refreshTradingListView() throws IOException {
        String shippingDate = "", shippingAddress = "";
        DefaultTableModel defaultTableModel = (DefaultTableModel) jTable_trading.getModel();
        if (null != defaultTableModel) {
            defaultTableModel.setRowCount(0);
        }
        List<ItemShortInfo> itemList = Utility.getProducts("https://fril.jp/ajax/item/trading");
        for (int i = 0; i < itemList.size(); i++) {

            URL url = new URL(itemList.get(i).strImageLink);
            HttpsURLConnection req = (HttpsURLConnection) url.openConnection();
            req.setRequestMethod("GET");
            
            //get shipping date information
            String tradingFullPage = getTradingFullPage(itemList.get(i).strHref);
            int startIndex = tradingFullPage.indexOf("<div class=\"card\">");
            if (startIndex > 0) {
                String cardSection = tradingFullPage.substring(startIndex);
                String shippingDeadLineText = Utility.extractAttribute(cardSection, 0, "<div class=\"col s5\">", "</div>");
                shippingDeadLineText = shippingDeadLineText.trim();
                shippingDate = Utility.extractAttribute(cardSection, 0, "<span class=\"large-text\">", "</br></span>");
            }

            int startIndex2 = tradingFullPage.indexOf("<!-- _address_info -->");
            if (startIndex2 > 0) {
                String addressInfoSection = tradingFullPage.substring(startIndex2);
                String partern = "<div class=\"col s12\">";
                int startIndex3 = addressInfoSection.indexOf(partern);
                if (startIndex3 > 0) {
                    shippingAddress = Utility.extractAttribute(addressInfoSection.substring(startIndex3),
                            partern.length() + 1, partern, "</div>");
                    shippingAddress = shippingAddress.replace("<p>", "");
                    shippingAddress = shippingAddress.replace("<\\p>", "");
                    shippingAddress = shippingAddress.replace("<br />", "\n");
                }
            }

            //get image
            BufferedImage bufferedImage = null;
            ImageIcon image;
            try {
                bufferedImage = ImageIO.read(req.getInputStream());
                image = new ImageIcon(bufferedImage);
                Image image2 = image.getImage();
                Image image3 = image2.getScaledInstance(150, 150, Image.SCALE_SMOOTH); //resize image
                ImageIcon imageicon2 = new ImageIcon(image3);

                if (null != bufferedImage) {
                    defaultTableModel.addRow(new Object[]{imageicon2, itemList.get(i).strHref, itemList.get(i).strMediaHeading, itemList.get(i).strWaiting, shippingDate, shippingAddress});
                } else {
                    defaultTableModel.addRow(new Object[]{itemList.get(i).strHref, itemList.get(i).strMediaHeading, itemList.get(i).strWaiting, shippingDate, shippingAddress});
                }
            } catch (IOException e) {
            
            }      
        }
    }

    public static void refreshListView() throws IOException {
        DefaultTableModel defaultTableModel = (DefaultTableModel) jTable_exhibition.getModel();
        if (null != defaultTableModel) {
            defaultTableModel.setRowCount(0);
        }
        List<ItemShortInfo> itemList = Utility.getProducts("https://fril.jp/ajax/item/selling");
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
                    defaultTableModel.addRow(new Object[]{imageicon2, itemList.get(i).strHref, itemList.get(i).strMediaHeading, itemList.get(i).strWaiting, "Click to edit"});
                } else {
                    defaultTableModel.addRow(new Object[]{itemList.get(i).strHref, itemList.get(i).strMediaHeading, itemList.get(i).strWaiting, "Click to edit"});
                }
            } catch (IOException e) {

            }
//          jTable_exhibition.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            int rowHeight = jTable_exhibition.getRowHeight();
            for (int column = 0; column < jTable_exhibition.getColumnCount() - 1; column++) {
                TableColumn tableColumn = jTable_exhibition.getColumnModel().getColumn(column);
                int preferredWidth = tableColumn.getMinWidth();
                int maxWidth = tableColumn.getMaxWidth();

                for (int row = 0; row < jTable_exhibition.getRowCount(); row++) {
                    TableCellRenderer cellRenderer = jTable_exhibition.getCellRenderer(row, column);
                    Component c = jTable_exhibition.prepareRenderer(cellRenderer, row, column);
                    int width = c.getPreferredSize().width + jTable_exhibition.getIntercellSpacing().width;
                    preferredWidth = Math.max(preferredWidth, width);
                    rowHeight = Math.max(c.getPreferredSize().height, rowHeight);
                    //  We've exceeded the maximum width, no need to check other rows
                    if (preferredWidth >= maxWidth) {
                        preferredWidth = maxWidth;
                        break;
                    }
                }
                jTable_exhibition.setRowHeight(rowHeight);
                tableColumn.setPreferredWidth(preferredWidth);
            }
        }
    }

    private static String getTradingFullPage(String strLink) throws MalformedURLException, IOException {
        String[] strTmp = strLink.split("/");
        String strLink2 = "https://fril.jp/" + strTmp[strTmp.length - 1];
        String fullPage = "";

        URL url = new URL(strLink2);
        HttpsURLConnection req = (HttpsURLConnection) url.openConnection();
        req.setRequestMethod("GET");
        req.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        req.setRequestProperty("Cookie", Utility.gCookieID);
        req.setRequestProperty("Referer", "https://fril.jp/sell");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(req.getInputStream()));
        String inputLine;
        StringBuilder stringBuilder = new StringBuilder();
        while ((inputLine = bufferedReader.readLine()) != null) {
            stringBuilder.append(inputLine);
        }
        fullPage = stringBuilder.toString();
        return fullPage;
    }

    //TODO
//    private boolean GetGrant2Access() throws SocketException {
//        //get Mac address of the computer running software        
//        String macAddress = Utility.GetMACAddress2();
//        String connectionString = Settings.licenseConnectionString;
//        return macAddress;
//    }
    void myTimerTick() {
        try {
            refreshListView();
        } catch (IOException ex) {
            Logger.getLogger(ListItems.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
