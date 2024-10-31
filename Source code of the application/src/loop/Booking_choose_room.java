/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loop;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Frank
 */
public class Booking_choose_room extends javax.swing.JFrame {

    public Connection cn;
    public Statement st;
    private String room_list = "";
    private boolean search_bol;
    private int cost_each_room ;
    private int dd =0;
   
     // updates app the data according to the data entered in all the tables 
    public void update()
    {
        get_set_data pop = new get_set_data();
        pop.main(cn);
        System.out.println("Done");
    }

    public void RoomType_combobox()
    {
         try{
            String sql = ("SELECT `Room_type` FROM `rooms` ");
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                String top = rs.getString("Room_type");
                roomType_combobox.addItem(top);
               
                
             
            }
        }
        catch(SQLException e){ JOptionPane.showMessageDialog(null, e);}
    }
    
    // get the bookings in the span of this current booking 
    private void get_bookings()
    {
        try
        {
            String sql = "SELECT * FROM `bookings`";
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                boolean cc1 = _Booked_from.equals("");
                boolean cc2 = _Booked_to.equals(""); 
                
                if (cc1 == false && cc2 == false)
                 {
                // get & set all the variables 
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String bookedrm = rs.getString("Booked_rooms");
                String datef = rs.getString("Booked_from");
                String datet = rs.getString("Booked_to");
                //set check booleans 
                boolean c1 = (_Booked_from.compareTo(datef))>=0;
                boolean c2 = (_Booked_to.compareTo(datef))>=0;
                boolean c3 = (_Booked_from.compareTo(datet))>=0;
                boolean c4 = (_Booked_to.compareTo(datet))>=0;
                

                // compare the checks
                int t = 0;
                if(c1 == false && c2 == true && c3 == false && c4 == true){t++;}
                if(c1 == true && c2 == true && c3 == false && c4 == false){t++;}
                if(c1 == false && c2 == true && c3 == false && c4 == false){t++;}
                if(c1 == true && c2 == true && c3 == false && c4 == true){t++;}
                if(t>0){room_list = room_list + bookedrm;}
                 }
                 else{System.out.println("something here");}
                 
            }
            
        }
        catch(SQLException e )
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    
      // get room type cost 
    private void get_RT_cost(String RT)
    {
        try
        {
            String sql ="SELECT * FROM `rooms`WHERE `Room_type` = '"+RT+"'";
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                cost_each_room = rs.getInt("cost_per_night");
                array_of_bookedrooms( room_list,rs.getInt("cost_per_night"),RT);
            }
        }
        catch(SQLException e )
        {
            JOptionPane.showMessageDialog(null, "1");
        }
    }
    
    
    //get all the booked rooms and puts them in an array
    private void array_of_bookedrooms(String RM,int RT_cost ,String RT)
    {
        
        int num_words = 0;
        // array to store each room
                String t2 ="";
                if(RM.equals("")!= true)
                {
                int y = RM.length();
                // to get the tot number of rooms in booking
                for(int z=0; z<y ; z++)
                {
                    String t =RM.substring(z,z+1);
                    if(t.equals(","))
                    {
                        num_words++;
                    }
                }
                String[] list = new String[num_words];
                // array to store each room
                int ct = 0;
                //breaks all the roooms and puts them into the array
                for(int z=0; z<y ; z++)
                {
                    String t = RM.substring(z,z+1);
                    
                    if(t.equals(","))
                    {
                        list[ct] = t2;
                        ct++;
                        t2 ="";
                    }
                    else
                    {
                        t2 = t2+t ;
                    }
                }
                set_table(RT, RT_cost, list, num_words);
                }
                else
                {
                    String[] list =new String[1];
                    list[0]="";
                    set_table(RT, RT_cost, list, num_words);
                }
    }
    
  private void set_table(String RT, int RT_cost, String[] rnl, int num )
  {
      
                
                 DefaultTableModel model = (DefaultTableModel)roomChooser_table.getModel();
                    try 
                    {
                        String sql = "SELECT *FROM `each_room` WHERE  `room_type` ='"+RT+"'";
                        PreparedStatement ps = cn.prepareStatement(sql);
                        ResultSet rs = ps.executeQuery();
                        
                        while(rs.next())
                        {
                            String ser = rs.getString("room_in_out_service");
                           if(ser.equals("In Service")){
                            String rn = rs.getString("room_no");
                            if(num >0)
                            {
                                int ch = 0;
                                for(int x = 0 ; x<num ; x++)
                                 { String t = rnl[x];
                                     if(rn.equals(t)== true)
                                {
                                    ch++;
                                }    
                                 }
                                if(ch == 0)
                                {
                                    model.addRow(new Object[] {rn,RT,RT_cost,false});
                                }
                            }
                            else
                            {
                                model.addRow(new Object[] {rn,RT,RT_cost,false});
                                
                            }
                            }
                           
                        }
                    }
                    catch(SQLException e)
                    {
                        JOptionPane.showMessageDialog(null, e);
                    }
  }
    
   
   
    
    
    public Booking_choose_room() {
        initComponents();
        this.setLocationRelativeTo(null);
       
        update();
        RoomType_combobox();
        search_bol = true;
        get_bookings();
        Error_roomNoSelect.setVisible(false);
    }
    
    private String _Booked_by_firstname ="";
    private String _Booked_by_lastname="";
    private String _childerNO="";
    private String _adultNo="";
    private String _Booked_from;
    private String _Booked_to;
    private byte[] image_indentification ;
    private String username;
    
    
    public Booking_choose_room(String Booked_by_firstname,String Booked_by_lastname,String childerNO,String adultNo,String Booked_from,String Booked_to, String _username,byte[] _image, Connection con) {
        initComponents();
        this.setLocationRelativeTo(null);
        cn = con;
        
        
        _Booked_by_firstname =Booked_by_firstname;
        _Booked_by_lastname=Booked_by_lastname;
        _childerNO=childerNO;
        _adultNo=adultNo;
        _Booked_from=Booked_from;
        _Booked_to=Booked_to;
        username =_username;
        image_indentification = _image;
        search_bol = true;
        
        
        Error_roomNoSelect.setVisible(false);
        update();
        
        RoomType_combobox();
        get_bookings();
        
           
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Master_pane = new javax.swing.JPanel();
        closeButton = new javax.swing.JButton();
        totalCost_lable = new javax.swing.JLabel();
        roomType_combobox = new javax.swing.JComboBox<>();
        roomtype_lable = new javax.swing.JLabel();
        search_button = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        roomChooser_table = new javax.swing.JTable();
        book_button = new javax.swing.JButton();
        closeButton1 = new javax.swing.JButton();
        reset_button = new javax.swing.JButton();
        Error_roomNoSelect = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(700, 700));

        Master_pane.setBackground(new java.awt.Color(255, 255, 255));
        Master_pane.setForeground(new java.awt.Color(255, 255, 255));
        Master_pane.setPreferredSize(new java.awt.Dimension(700, 700));

        closeButton.setBackground(new java.awt.Color(0, 102, 255));
        closeButton.setForeground(new java.awt.Color(255, 255, 255));
        closeButton.setText("X");
        closeButton.setToolTipText("");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        totalCost_lable.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        totalCost_lable.setForeground(new java.awt.Color(51, 102, 255));
        totalCost_lable.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalCost_lable.setText("Room Chooser");

        roomType_combobox.setBackground(new java.awt.Color(51, 102, 255));
        roomType_combobox.setForeground(new java.awt.Color(255, 255, 255));
        roomType_combobox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                roomType_comboboxMouseClicked(evt);
            }
        });
        roomType_combobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roomType_comboboxActionPerformed(evt);
            }
        });

        roomtype_lable.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        roomtype_lable.setForeground(new java.awt.Color(51, 102, 255));
        roomtype_lable.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        roomtype_lable.setText("Room Type");

        search_button.setBackground(new java.awt.Color(0, 102, 255));
        search_button.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        search_button.setForeground(new java.awt.Color(255, 255, 255));
        search_button.setText("Search");
        search_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_buttonActionPerformed(evt);
            }
        });
        search_button.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                search_buttonKeyPressed(evt);
            }
        });

        roomChooser_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Room No.", "Room Type", "Cost Per Night", "Select"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(roomChooser_table);

        book_button.setBackground(new java.awt.Color(0, 102, 255));
        book_button.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        book_button.setForeground(new java.awt.Color(255, 255, 255));
        book_button.setText("Book");
        book_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                book_buttonActionPerformed(evt);
            }
        });
        book_button.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                book_buttonKeyPressed(evt);
            }
        });

        closeButton1.setBackground(new java.awt.Color(0, 102, 255));
        closeButton1.setForeground(new java.awt.Color(255, 255, 255));
        closeButton1.setText("<");
        closeButton1.setToolTipText("");
        closeButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButton1ActionPerformed(evt);
            }
        });

        reset_button.setBackground(new java.awt.Color(0, 102, 255));
        reset_button.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        reset_button.setForeground(new java.awt.Color(255, 255, 255));
        reset_button.setText("Reset");
        reset_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reset_buttonActionPerformed(evt);
            }
        });
        reset_button.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                reset_buttonKeyPressed(evt);
            }
        });

        Error_roomNoSelect.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Error_roomNoSelect.setForeground(new java.awt.Color(255, 0, 0));
        Error_roomNoSelect.setText("*Please Select Room/s*");

        javax.swing.GroupLayout Master_paneLayout = new javax.swing.GroupLayout(Master_pane);
        Master_pane.setLayout(Master_paneLayout);
        Master_paneLayout.setHorizontalGroup(
            Master_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Master_paneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Master_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Master_paneLayout.createSequentialGroup()
                        .addGroup(Master_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(totalCost_lable)
                            .addGroup(Master_paneLayout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addComponent(roomtype_lable)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(Master_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Master_paneLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(closeButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(Master_paneLayout.createSequentialGroup()
                                .addComponent(roomType_combobox, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(search_button, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 108, Short.MAX_VALUE))))
                    .addGroup(Master_paneLayout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(Master_paneLayout.createSequentialGroup()
                        .addComponent(reset_button, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(99, 99, 99)
                        .addComponent(Error_roomNoSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Master_paneLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(book_button, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(268, 268, 268))))
        );
        Master_paneLayout.setVerticalGroup(
            Master_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Master_paneLayout.createSequentialGroup()
                .addGroup(Master_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Master_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(closeButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(Master_paneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(Master_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(Master_paneLayout.createSequentialGroup()
                                .addComponent(totalCost_lable)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(roomtype_lable)
                                .addGap(12, 12, 12))
                            .addGroup(Master_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(roomType_combobox, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(search_button, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Master_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(reset_button, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Error_roomNoSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(book_button, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(87, 87, 87))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Master_pane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Master_pane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 706, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_closeButtonActionPerformed

    private void roomType_comboboxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_roomType_comboboxMouseClicked

    }//GEN-LAST:event_roomType_comboboxMouseClicked

    private void roomType_comboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roomType_comboboxActionPerformed

    }//GEN-LAST:event_roomType_comboboxActionPerformed

    private void search_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_buttonActionPerformed

        Error_roomNoSelect.setVisible(false);
        DefaultTableModel model = (DefaultTableModel)roomChooser_table.getModel();
        String roomtype = roomType_combobox.getSelectedItem().toString();
        int row_ct = model.getRowCount();
        
       if(search_bol)
       {
           
       while(row_ct > 0)
             {
              model.removeRow(0);
             }
       get_RT_cost(roomtype);
       
       search_bol =false;
       }
       else
       {
           
           int ch = 0;
           for(int x = 0 ; x < row_ct; x++ )
           {
               String t = model.getValueAt(x, 1).toString();
               if(t.equals(roomtype))
               {
                   ch = ch+1 ;
               }
           }
           if(ch == 0)
           {
               get_RT_cost(roomtype);
           }
       }
       
    }//GEN-LAST:event_search_buttonActionPerformed

    private void search_buttonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_buttonKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_search_buttonKeyPressed

    private void book_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_book_buttonActionPerformed
        // TODO add your handling code here:
        //Get the table model and reffer it to the table in the booking_choose_room pane
        DefaultTableModel model = (DefaultTableModel)roomChooser_table.getModel();
        //Array to hold the booked rooms
        String[] RoomNos = new String[model.getRowCount()]  ;
         int count=0;
         //Cycle through the displayed table and check whihc rooms are chosen
        while (model.getRowCount() > 0)
        {
            if (model.getValueAt(0, 3).toString().equals("true")){count++; RoomNos[count]= model.getValueAt(0, 0).toString(); }
            model.removeRow(0);
        }
        String t = String.valueOf(cost_each_room);
        int costEachRoom = Integer.parseInt(t) ; 
        int Total_cost = costEachRoom*count; 
        // check if the count > 0, as that will signify that there are rooms chosen to book if not then a error is displayed
        if(count > 0){
        new booking_confirm(_Booked_by_firstname, _Booked_by_lastname, _childerNO, _adultNo, _Booked_from, _Booked_to,RoomNos,Total_cost,count,image_indentification, cn).setVisible(true);
        }
        else
        {
            Error_roomNoSelect.setVisible(true);
        }
       
    }//GEN-LAST:event_book_buttonActionPerformed

    private void book_buttonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_book_buttonKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_book_buttonKeyPressed

    private void closeButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButton1ActionPerformed
        // TODO add your handling code here:

        new mainMenu(username,cn).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_closeButton1ActionPerformed

    private void reset_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reset_buttonActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel)roomChooser_table.getModel();
        Error_roomNoSelect.setVisible(false);
       
       while(model.getRowCount() > 0)
             {
              model.removeRow(0);
             }
        search_bol =true;
    }//GEN-LAST:event_reset_buttonActionPerformed

    private void reset_buttonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_reset_buttonKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_reset_buttonKeyPressed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(Booking_choose_room.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Booking_choose_room.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Booking_choose_room.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Booking_choose_room.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Booking_choose_room().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Error_roomNoSelect;
    private javax.swing.JPanel Master_pane;
    private javax.swing.JButton book_button;
    private javax.swing.JButton closeButton;
    private javax.swing.JButton closeButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton reset_button;
    private javax.swing.JTable roomChooser_table;
    private javax.swing.JComboBox<String> roomType_combobox;
    private javax.swing.JLabel roomtype_lable;
    private javax.swing.JButton search_button;
    private javax.swing.JLabel totalCost_lable;
    // End of variables declaration//GEN-END:variables
}
