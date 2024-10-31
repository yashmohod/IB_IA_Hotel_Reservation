/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loop;
import java.awt.HeadlessException;
import java.sql.*;
import java.util.Arrays;
import javax.sql.rowset.serial.SerialBlob;
import javax.swing.*;



public final class booking_confirm extends javax.swing.JFrame {

    public Connection cn;
    public Statement st;
    public int Set_index ;
/*
        try {
            cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/loop_hotel?zeroDateTimeBehavior=convertToNull", "root", "");
            st = cn.createStatement();
            System.out.println("conected");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "not conected");
        }
    }*/
    
    
    ///get greatest index and set the next index
    public void getset_index()
    {
        try
        {
            String sql = "SELECT MAX(`Ind_book`) FROM `bookings`";
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                int index = rs.getInt(1);
                 Set_index = index + 1;
                 System.out.println(Set_index);
                
            }
            
        }
        catch (Exception e)
        {
           JOptionPane.showMessageDialog(null, "1");
        }
                    
                    
                    
    }
    
    
    
     
    public void Set_Booking(String Booked_by_firstname,String Booked_by_lastname,String childerNO,String adultNo,String Booked_from,
            String Booked_to, String AllRoomNos,int total_cost) throws SQLException 
    {
        int child_no = Integer.parseInt(childerNO);
        int adult_no = Integer.parseInt(adultNo);
        //set new index number for the given booking
        getset_index();
        
        //prepare statement 
        String sql =("INSERT INTO `bookings`(`Booked_by_firstname`, `Booked_by_lastname`, `Booked_from`, `Booked_to`, `Booked_rooms`, "
                + "`Total_cost`, `No. of Children`, `No. of Adults`,`Ind_book`,`identification`,`stat`) VALUES (?,?,?,?,?,?,?,?,?,?,?)") ;
        PreparedStatement ps = cn.prepareStatement(sql);

       try {
       //set values
        ps.setString(1,Booked_by_firstname );
        ps.setString(2,Booked_by_lastname );
        ps.setString(3,Booked_from );
        ps.setString(4,Booked_to );
        ps.setString(5,AllRoomNos );
        ps.setInt(6,total_cost );
        ps.setInt(7,child_no );
        ps.setInt(8,adult_no );
        ps.setInt(9,Set_index );
        ps.setBytes(10, image_indentification);
        ps.setString(11,"yet");
        // execute query
        ps.execute();
        
        JOptionPane.showMessageDialog(null, "submitted");
       }
       catch (HeadlessException | SQLException e){System.out.println(e);JOptionPane.showMessageDialog(null, "here" +e);}
    }
    
    
    
    
    public booking_confirm() {
        initComponents();
        this.setLocationRelativeTo(null);
        
    }
    
    
   
    
    
    private String _Booked_by_firstname ="";
    public String _Booked_by_lastname="";
    public String _childerNO="";
    public String _adultNo="";
    public String _Booked_from="";
    public String _Booked_to ="";
    public int count = 0;
    public int Totalcost=0;
    public String[] _All_Room_Nos  = new String[count]  ;
    public byte[] image_indentification  ;
    
    public booking_confirm(String Booked_by_firstname,String Booked_by_lastname,String childerNO,String adultNo,String Booked_from,String Booked_to, String[] AllRoomNos,int total_cost,int c,byte[] _image, Connection con) {
        initComponents();
        
        this.setLocationRelativeTo(null);
        cn = con;
        _Booked_by_firstname =Booked_by_firstname;
        _Booked_by_lastname=Booked_by_lastname;
        _childerNO=childerNO;
        _adultNo=adultNo;
        _Booked_from=Booked_from;
        _Booked_to=Booked_to;
       count = c;
       Totalcost=total_cost;
       _All_Room_Nos =AllRoomNos ;
       String displayCost = Integer.toString(total_cost);
       totalCost_textbox.setText(displayCost);
       image_indentification = _image;
        
        
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        closeButton = new javax.swing.JButton();
        totalCost_lable1 = new javax.swing.JLabel();
        totalCost_textbox = new javax.swing.JTextField();
        book_button = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        closeButton.setBackground(new java.awt.Color(0, 102, 255));
        closeButton.setForeground(new java.awt.Color(255, 255, 255));
        closeButton.setText("X");
        closeButton.setToolTipText("");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        totalCost_lable1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        totalCost_lable1.setForeground(new java.awt.Color(51, 102, 255));
        totalCost_lable1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalCost_lable1.setText("Total Cost :-");

        totalCost_textbox.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        totalCost_textbox.setEnabled(false);
        totalCost_textbox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                totalCost_textboxMouseClicked(evt);
            }
        });
        totalCost_textbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalCost_textboxActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(119, 119, 119)
                .addComponent(book_button, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(totalCost_lable1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(totalCost_textbox, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(93, 93, 93)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalCost_textbox, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalCost_lable1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                .addComponent(book_button, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_closeButtonActionPerformed

    private void totalCost_textboxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_totalCost_textboxMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_totalCost_textboxMouseClicked

    private void totalCost_textboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalCost_textboxActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_totalCost_textboxActionPerformed

    private void book_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_book_buttonActionPerformed

        int count2 = count;
        String All_Room_Nos="";
        while(count2>0)
        {
            All_Room_Nos = All_Room_Nos+ _All_Room_Nos[count2]+",";
            count2--;
        }
        try {
            Set_Booking(_Booked_by_firstname, _Booked_by_lastname ,  _childerNO ,  _adultNo ,  _Booked_from , _Booked_to ,All_Room_Nos , Totalcost);
        } catch (Exception ex) {
            System.out.println("3");
        }
        this.dispose();
    }//GEN-LAST:event_book_buttonActionPerformed

    private void book_buttonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_book_buttonKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_book_buttonKeyPressed

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
            java.util.logging.Logger.getLogger(booking_confirm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(booking_confirm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(booking_confirm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(booking_confirm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new booking_confirm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton book_button;
    private javax.swing.JButton closeButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel totalCost_lable1;
    private javax.swing.JTextField totalCost_textbox;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the _Booked_by_firstname
     */
    public String getBooked_by_firstname() {
        return _Booked_by_firstname;
    }

    /**
     * @param _Booked_by_firstname the _Booked_by_firstname to set
     */
    public void setBooked_by_firstname(String _Booked_by_firstname) {
        this._Booked_by_firstname = _Booked_by_firstname;
    }
}
